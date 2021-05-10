package com.example.madguidesapp.android.viewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.HotelCategory;
import com.example.madguidesapp.pojos.MainMenuElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.Suggestion;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.repository.FirestorageRepository;
import com.example.madguidesapp.repository.FirestoreRepository;
import com.example.madguidesapp.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawerActivityViewModel extends ViewModel {
    private static final String TAG = "DrawerActivityViewModel";
    private FirestoreRepository firestoreRepository;
    private FirestorageRepository firestorageRepository;

    public DrawerActivityViewModel(){
        firestoreRepository = new FirestoreRepository();
        firestorageRepository = new FirestorageRepository();

        initializeAll();
    }

    public void initializeAll(){
        initializeMainMenuViewModel();
        initializeUserViewModel();
        initializeResourcesViewModel();
        initializeRoutesViewModel();
        initializeGuidesViewModel();
        initializeHotelsViewModel();
    }

    //region User view model
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<List<RecyclerViewElement>> favoritesMutableLiveData;
    private UserRepository userRepository;

    private void initializeUserViewModel(){
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

    public Task<QuerySnapshot> usernameExists(String username){
        return userRepository.usernameExists(username);
    }

    public void setUser(){
        userRepository.getUser().
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult().toObject(User.class);
                        userMutableLiveData.setValue(user);
                        initializeFavorites();
                        removeUserFromGuides(user.getEmail());
                    }
                });
    }

    public Task<AuthResult> signIn(String email, String password){
        return userRepository.signIn(email, password).
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
        restoreGuidesList();
    }

    public Task register(User user, String password){
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

        return userRepository.register(user, password).addOnCompleteListener(registerCompleted);
    }

    public void initializeFavorites(){
        favoritesMutableLiveData.setValue(new ArrayList<>());

        if(!areUserRegistered()){
            return;
        }

        for(DocumentReference ref:userMutableLiveData.getValue().getFavorites()) {
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<RecyclerViewElement> favorites = favoritesMutableLiveData.getValue();

                    if (ref.getParent().getId().contains("Resources")) {
                        favorites.add(task.getResult().toObject(Resource.class));
                    }
                    else if(ref.getParent().getId().contains("Routes")){
                        favorites.add(task.getResult().toObject(Route.class));
                    }
                    else if(ref.getParent().getId().contains("Hotels")){
                        favorites.add(task.getResult().toObject(Hotel.class));
                    }

                    favorites = favorites.stream().
                            sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                            collect(Collectors.toList());

                    favoritesMutableLiveData.setValue(favorites);
                }
            });
        }
    }

    public boolean isVisited(RecyclerViewElement recyclerViewElement){
        if(recyclerViewElement instanceof Resource){
            return userMutableLiveData.getValue().getVisitedResources().contains(recyclerViewElement.getName());
        }
        else if(recyclerViewElement instanceof Route){
            return userMutableLiveData.getValue().getVisitedRoutes().contains(recyclerViewElement.getName());
        }

        return false;
    }

    public void toggleVisited(RecyclerViewElement recyclerViewElement){
        if(recyclerViewElement instanceof Resource){
            toggleResourceVisited(recyclerViewElement.getName());
        }
        else if(recyclerViewElement instanceof Route){
            toggleRouteVisited(recyclerViewElement.getName());
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

    private void toggleResourceVisited(String name){
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
                        }
                    });
        }
    }
    private void toggleRouteVisited(String name){
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
    private MediatorLiveData<List<Resource>> filteredResourcesMediatorLiveData;
    private boolean areResourcesFiltered = false;

    private void initializeResourcesViewModel(){
        resourcesMutableLiveData = new MutableLiveData<>();
        filteredResourcesMediatorLiveData = new MediatorLiveData<>();

        filteredResourcesMediatorLiveData.
                addSource(userMutableLiveData, user -> {
                    if(user != null){
                        List<Resource> filteredResources = resourcesMutableLiveData.getValue().stream().
                                filter(resource -> user.getVisitedResources().contains(resource.getName())).
                                collect(Collectors.toList());

                        filteredResourcesMediatorLiveData.setValue(filteredResources);
                    }
                });

        firestoreRepository.getResources().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        resourcesMutableLiveData.setValue(task.getResult().toObjects(Resource.class));
                    }
                });
    }

    public void setResourcesFilter(boolean filter){ areResourcesFiltered = filter; }

    public LiveData<List<Resource>> getResourcesLiveData(){
        return (areResourcesFiltered) ? filteredResourcesMediatorLiveData : resourcesMutableLiveData;
    }
    //endregion

    //region Routes view model
    private MutableLiveData<List<Route>> routesMutableLiveData;
    private MediatorLiveData<List<Route>> filteredRoutesMediatorLiveDataLiveData;
    private boolean areRoutesFiltered = false;

    private void initializeRoutesViewModel(){
        routesMutableLiveData = new MutableLiveData<>();
        filteredRoutesMediatorLiveDataLiveData = new MediatorLiveData<>();

        filteredRoutesMediatorLiveDataLiveData.
                addSource(userMutableLiveData, user -> {
                    if(user != null) {
                        List<Route> filteredRoutes = routesMutableLiveData.getValue().stream().
                                filter(route -> user.getVisitedRoutes().contains(route.getName())).
                                collect(Collectors.toList());

                        filteredRoutesMediatorLiveDataLiveData.setValue(filteredRoutes);
                    }
                });

        firestoreRepository.getRoutes().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                routesMutableLiveData.setValue(task.getResult().toObjects(Route.class));
            }
        });
    }

    public void setRoutesFilter(boolean filter){ areRoutesFiltered = filter; }

    public LiveData<List<Route>> getRoutesLiveData(){
        return (areRoutesFiltered) ? filteredRoutesMediatorLiveDataLiveData : routesMutableLiveData;
    }
    //endregion

    //region Guides view model
    private List<Guide> guidesFullList = new ArrayList<>();
    private MutableLiveData<List<Guide>> filteredGuidesMutableLiveData;

    private void initializeGuidesViewModel(){
        firestoreRepository = new FirestoreRepository();
        filteredGuidesMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getGuides().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        guidesFullList = task.getResult().toObjects(Guide.class);

                        filteredGuidesMutableLiveData.setValue(guidesFullList);
                    }
                });
    }

    public void removeUserFromGuides(String userEmail){
        List<Guide> guidesFiltered = guidesFullList.stream().
                filter(guide -> !guide.getEmail().equals(userEmail)).
                collect(Collectors.toList());

        filteredGuidesMutableLiveData.setValue(guidesFiltered);
    }

    public void restoreGuidesList(){
        filteredGuidesMutableLiveData.setValue(guidesFullList);
    }

    public LiveData<List<Guide>> getGuidesLiveData(){
        return filteredGuidesMutableLiveData;
    }
    //endregion

    //region hotels view model
    private MutableLiveData<List<HotelCategory>> hotelCategoriesMutableLiveData;
    private MutableLiveData<List<Hotel>> hotelsMutableLiveData;

    public void initializeHotelsViewModel(){
        firestoreRepository = new FirestoreRepository();

        hotelCategoriesMutableLiveData = new MutableLiveData<>();
        hotelsMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getHotelCategories().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        hotelCategoriesMutableLiveData.setValue(
                                task.getResult().toObjects(HotelCategory.class));
                    }
                });
    }

    public void filterHotels(int categoryFilter){
        hotelsMutableLiveData.setValue(new ArrayList<>());

        firestoreRepository.getHotels(categoryFilter).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        hotelsMutableLiveData.setValue(task.getResult().toObjects(Hotel.class));
                    }
                });
    }

    public LiveData<List<HotelCategory>> getHotelCategoriesLiveData(){
        return hotelCategoriesMutableLiveData;
    }

    public LiveData<List<Hotel>> getHotelsLiveData(){
        return hotelsMutableLiveData;
    }
    //endregion

    //region Main menu view model
    private MutableLiveData<List<MainMenuElement>> mainMenuElements;

    public void initializeMainMenuViewModel(){
        firestoreRepository = new FirestoreRepository();
        mainMenuElements = new MutableLiveData<>();

        firestoreRepository.getMainMenuElements().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mainMenuElements.setValue(task.getResult().toObjects(MainMenuElement.class));
                    }
                });
    }

    public LiveData<List<MainMenuElement>> getMainMenuElements(){
        return mainMenuElements;
    }
    //endregion

    //region Suggestions view model
    public UploadTask uploadSuggestionImage(Uri suggestionFileUri){
        return firestorageRepository.uploadSuggestionImage(suggestionFileUri);
    }

    public Task<Void> uploadSuggestion(Suggestion suggestion){
        return firestoreRepository.uploadSuggestion(suggestion);
    }
    //endregion
}



















