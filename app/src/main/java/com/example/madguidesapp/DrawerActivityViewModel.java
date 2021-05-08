package com.example.madguidesapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DrawerActivityViewModel extends ViewModel {
    private static final String TAG = "DrawerActivityViewModel";
    private AppRepository appRepository;

    public DrawerActivityViewModel(){
        appRepository = new AppRepository();

        initializeUserViewModel();
        initializeResourcesViewModel();
        initializeRoutesViewModel();
    }

    //region User view model
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<List<RecyclerViewElement>> favoritesMutableLiveData;
    private UserRepository userRepository;

    public void initializeUserViewModel(){
        userRepository = new UserRepository();

        userMutableLiveData = new MutableLiveData<>();
        favoritesMutableLiveData = new MutableLiveData<>();

        if(areUserRegistered()) {
            setUser();
        }
    }

    public boolean areUserRegistered(){
        return userRepository.areUserRegistered();
    }

    public void setUser(){
        userRepository.getUser().
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult().toObject(User.class);
                        userMutableLiveData.setValue(user);
                        initializeFavorites();
                    }
                });
    }

    public void signIn(String email, String password){
        userRepository.signIn(email, password).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        setUser();
                    }
                });
    }

    public void signOut(){
        userRepository.signOut();
        userMutableLiveData.setValue(null);
        favoritesMutableLiveData.setValue(null);
    }

    public void register(User user, String password){
        OnCompleteListener creationCompleted = task -> {
            if(task.isSuccessful()){
                setUser();
            }
        };

        OnCompleteListener registerCompleted = task -> {
            if(task.isSuccessful()){
                userRepository.createUser(user).addOnCompleteListener(creationCompleted);
            }
        };

        userRepository.register(user, password).addOnCompleteListener(registerCompleted);
    }

    public void initializeFavorites(){
        favoritesMutableLiveData.setValue(new ArrayList<>());

        if(areUserRegistered()) {
            for(DocumentReference ref:userMutableLiveData.getValue().getFavorites()) {
                ref.get().addOnCompleteListener(task -> {
                    Log.d(TAG, "initializeFavorites: ");
                    if (task.isSuccessful()) {
                        List<RecyclerViewElement> favorites = favoritesMutableLiveData.getValue();

                        if (ref.getParent().getId().contains("Resources")) {
                            favorites.add(task.getResult().toObject(Resource.class));
                        }
                        if(ref.getParent().getId().contains("Routes")){
                            favorites.add(task.getResult().toObject(Route.class));
                        }

                        favorites = favorites.stream().
                                sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                                collect(Collectors.toList());

                        favoritesMutableLiveData.setValue(favorites);
                    }
                });
            }
        }
    }

    public void toggleFavorite(RecyclerViewElement recyclerViewElement){
        DocumentReference reference = userRepository.createReference(recyclerViewElement.toString());

        User user = userMutableLiveData.getValue();
        List<RecyclerViewElement> elements = favoritesMutableLiveData.getValue();

        boolean isFavorite = elements.contains(recyclerViewElement);

        if(isFavorite){
            userRepository.removeReference(reference).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            user.getFavorites().remove(recyclerViewElement);
                            elements.remove(recyclerViewElement);

                            favoritesMutableLiveData.setValue(elements);
                            userMutableLiveData.setValue(user);
                        }
                    });
        }
        else{
            userRepository.addReference(reference).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            user.getFavorites().add(reference);
                            elements.add(recyclerViewElement);

                            favoritesMutableLiveData.setValue(elements);
                            userMutableLiveData.setValue(user);
                        }
                    });
        }
    }
    public void toggleResourceVisited(String name){
        User user = userMutableLiveData.getValue();
        List<String> resources = user.getVisitedResources();

        boolean isVisited = resources.contains(name);

        if(isVisited){
            userRepository.removeResource(name).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            resources.remove(name);
                            user.setVisitedResources(resources);

                            userMutableLiveData.setValue(user);
                            filterResources();
                        }
                    });
        }
        else{
            userRepository.addResource(name).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            resources.add(name);
                            user.setVisitedResources(resources);

                            userMutableLiveData.setValue(user);
                            filterResources();
                        }
                    });
        }
    }
    public void toggleRouteVisited(String name){
        User user = userMutableLiveData.getValue();
        List<String> routes = user.getVisitedRoutes();

        boolean isVisited = routes.contains(name);

        if(isVisited){
            userRepository.removeRoute(name).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            routes.remove(name);
                            user.setVisitedRoutes(routes);

                            userMutableLiveData.setValue(user);
                            filterResources();
                        }
                    });
        }
        else{
            userRepository.addRoute(name).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            routes.add(name);
                            user.setVisitedRoutes(routes);

                            userMutableLiveData.setValue(user);
                            filterResources();
                        }
                    });
        }
    }

    public LiveData<List<RecyclerViewElement>> getFavoritesLiveData(){
        return favoritesMutableLiveData;
    }

    public LiveData<User> getUserLiveData(){
        return userMutableLiveData;
    }
    //endregion

    //region Resources view model
    private MutableLiveData<List<Resource>> resourcesMutableLiveData;
    private MutableLiveData<List<Resource>> filteredResourcesMutableLiveData;
    private boolean isFiltered = false;

    public void initializeResourcesViewModel(){
        resourcesMutableLiveData = new MutableLiveData<>();
        filteredResourcesMutableLiveData = new MutableLiveData<>();

        appRepository.getResources().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        resourcesMutableLiveData.setValue(task.getResult().toObjects(Resource.class));
                    }
                });
    }

    public void filterResources(){
        isFiltered = true;
        List<Resource> filteredResources = resourcesMutableLiveData.getValue().stream().
                filter(resource -> userMutableLiveData.getValue().getVisitedResources().contains(resource.getName())).
                collect(Collectors.toList());

        filteredResourcesMutableLiveData.setValue(filteredResources);
    }

    public void clearFilters(){
        isFiltered = false;
    }

    public LiveData<List<Resource>> getResourcesLiveData(){
        return (isFiltered) ? filteredResourcesMutableLiveData : resourcesMutableLiveData;
    }
    //endregion

    //region Routes view model
    private MutableLiveData<List<Route>> routesMutableLiveData;

    public void initializeRoutesViewModel(){
        routesMutableLiveData = new MutableLiveData<>();

        appRepository.getRoutes().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                routesMutableLiveData.setValue(task.getResult().toObjects(Route.class));
            }
        });
    }

    public LiveData<List<Route>> getRoutesLiveData(){
        return routesMutableLiveData;
    }
    //endregion
}



















