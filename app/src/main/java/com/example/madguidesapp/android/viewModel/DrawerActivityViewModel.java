package com.example.madguidesapp.android.viewModel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.pojos.Comment;
import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.HotelCategory;
import com.example.madguidesapp.pojos.MainMenuElement;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Restaurant;
import com.example.madguidesapp.pojos.RestaurantCategory;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.Suggestion;
import com.example.madguidesapp.pojos.User;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        initializeRestaurants();
        initializeSuggestionsViewModel();
        initializeComments();
    }

    //region profile view model
    public void sendBecomeAGuideSolicitude(){
        User user = userMutableLiveData.getValue();

        user.setGuideStatus(0);

        OnCompleteListener updateCompleted = task -> {
            if(task.isSuccessful()){
                userMutableLiveData.setValue(user);
            }
        };

        OnCompleteListener sendCompleted = task -> {
            if(task.isSuccessful()) {
                userRepository.updateUser(user).addOnCompleteListener(updateCompleted);
            }
        };

        firestoreRepository.sendBecomeAGuideSolicitude(user.getEmail(), userRepository.getUserId()).addOnCompleteListener(sendCompleted);
    }

    public void updateProfilePhoto(Uri profilePhotoUri){
        OnCompleteListener<Uri> urlLoaded = task -> {
              if(task.isSuccessful()){
                  User user = userMutableLiveData.getValue();

                  assert user != null;
                  user.setImageUrl(task.getResult().toString());

                  userRepository.updateUser(user).
                          addOnCompleteListener(task1 -> userMutableLiveData.setValue(user));
              }
        };

        OnCompleteListener<UploadTask.TaskSnapshot> uploadComplete = task -> {
            if(task.isSuccessful()){
                task.getResult().getMetadata().getReference().getDownloadUrl().
                        addOnCompleteListener(urlLoaded);
            }
        };

        firestorageRepository.uploadProfileImage(profilePhotoUri).addOnCompleteListener(uploadComplete);
    }
    //endregion

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
        Log.d(TAG, "setUser: ");
        userRepository.getUser().
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult().toObject(User.class);
                        userMutableLiveData.setValue(user);
                        initializeFavorites();
                        removeUserFromGuides(userRepository.getUserId());
                        fillGuide();
                    }
                });
    }

    public void updateUser(User user){
        userRepository.updateUser(user).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        userMutableLiveData.setValue(user);
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
        registeredGuideMutableLiveData.setValue(null);
    }

    public Task<AuthResult> register(User user, String password){
        OnCompleteListener<Void> creationCompleted = task -> {
            if(task.isSuccessful()){
                setUser();
            }
        };

        return userRepository.register(user, password).
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userRepository.createUser(user, task.getResult().getUser().getUid()).
                                addOnCompleteListener(creationCompleted);
                    }
                });
    }

    public void initializeFavorites(){
        favoritesMutableLiveData.setValue(new ArrayList<>());

        Log.d(TAG, "initializeFavorites: "+userMutableLiveData.getValue());

        if(!areUserRegistered() && userMutableLiveData.getValue().getFavorites() != null){
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

    public boolean isFavorite(RecyclerViewElement recyclerViewElement){
        return favoritesMutableLiveData.getValue().contains(recyclerViewElement);
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
                            filterRoutes();
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
                            filterRoutes();
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
    private boolean areResourcesFiltered = false;

    private void initializeResourcesViewModel(){
        resourcesMutableLiveData = new MutableLiveData<>();
        filteredResourcesMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getResources().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        resourcesMutableLiveData.setValue(task.getResult().toObjects(Resource.class));
                    }
                    else{
                        Log.d(TAG, "initializeResourcesViewModel: "+task.getException());
                        resourcesMutableLiveData.setValue(new ArrayList<>());
                    }
                });
    }

    public void setResourcesFilter(boolean filter){
        areResourcesFiltered = filter;
    }

    public void filterResources(){
        List<Resource> filteredResources = resourcesMutableLiveData.getValue().stream().
                filter(resource -> userMutableLiveData.getValue().getVisitedResources().contains(resource.getName())).
                collect(Collectors.toList());

        Log.d(TAG, "filterResources: ");

        filteredResourcesMutableLiveData.setValue(filteredResources);
    }

    public LiveData<List<Resource>> getResourcesLiveData(){
        Log.d(TAG, "getResourcesLiveData: "+areResourcesFiltered);
        return (areResourcesFiltered) ? filteredResourcesMutableLiveData : resourcesMutableLiveData;
    }
    //endregion

    //region Routes view model
    private MutableLiveData<List<Route>> routesMutableLiveData;
    private MutableLiveData<List<Route>> filteredRoutesMutableLiveData;
    private boolean areRoutesFiltered = false;

    private void initializeRoutesViewModel(){
        routesMutableLiveData = new MutableLiveData<>();
        filteredRoutesMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getRoutes().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                routesMutableLiveData.setValue(task.getResult().toObjects(Route.class));
            }
        });
    }

    public void setRoutesFilter(boolean filter){ areRoutesFiltered = filter; }

    public void filterRoutes(){
        List<Route> filteredRoutes = routesMutableLiveData.getValue().stream().
                filter(route -> userMutableLiveData.getValue().getVisitedRoutes().contains(route.getName())).
                collect(Collectors.toList());

        filteredRoutesMutableLiveData.setValue(filteredRoutes);
    }

    public LiveData<List<Route>> getRoutesLiveData(){
        return (areRoutesFiltered) ? filteredRoutesMutableLiveData : routesMutableLiveData;
    }
    //endregion

    //region Guides view model
    private List<Guide> guidesFullList = new ArrayList<>();
    private MutableLiveData<Guide> registeredGuideMutableLiveData;
    private MutableLiveData<List<Guide>> filteredGuidesMutableLiveData;

    private void initializeGuidesViewModel(){
        filteredGuidesMutableLiveData = new MutableLiveData<>();
        registeredGuideMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getGuides().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        guidesFullList = task.getResult().toObjects(Guide.class);

                        filteredGuidesMutableLiveData.setValue(guidesFullList);
                    }
                });
    }

    public void removeUserFromGuides(String id){
        assert userMutableLiveData.getValue() != null;

        List<Guide> guidesFiltered = guidesFullList.stream().
                filter(guide -> !guide.getId().equals(id)).
                collect(Collectors.toList());

        filteredGuidesMutableLiveData.setValue(guidesFiltered);
    }

    public Task<Void> createGuideProfile(Map<String, Object> map){
        assert userMutableLiveData.getValue() != null;
        map.put("imageUrl", userMutableLiveData.getValue().getImageUrl());
        map.put("id", userRepository.getUserId());
        map.put("name", userMutableLiveData.getValue().getUsername());

        return firestoreRepository.insertGuide(map, userRepository.getUserId()).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        fillGuide();
                    }
                });
    }

    public void restoreGuidesList(){
        filteredGuidesMutableLiveData.setValue(guidesFullList);
    }

    public void fillGuide(){
        firestoreRepository.findGuide(userRepository.getUserId()).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        registeredGuideMutableLiveData.setValue(task.getResult().toObject(Guide.class));
                    }
                });
    }

    public LiveData<List<Guide>> getGuidesLiveData(){
        if(areUserRegistered()){
            removeUserFromGuides(userRepository.getUserId());
        }
        else{
            restoreGuidesList();
        }

        return filteredGuidesMutableLiveData;
    }

    public LiveData<Guide> getGuideLiveData(){
        return registeredGuideMutableLiveData;
    }
    //endregion

    //region hotels view model
    private MutableLiveData<List<HotelCategory>> hotelCategoriesMutableLiveData;
    private MutableLiveData<List<Hotel>> hotelsMutableLiveData;

    public void initializeHotelsViewModel(){
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

    //region restaurants view model
    private MutableLiveData<List<RestaurantCategory>> restaurantCategoriesMutableLiveData;
    private MutableLiveData<List<Restaurant>> restaurantsMutableLiveData;

    public void initializeRestaurants(){
        restaurantCategoriesMutableLiveData = new MutableLiveData<>();
        restaurantsMutableLiveData = new MutableLiveData<>();

        firestoreRepository.getRestaurantCategories().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        restaurantCategoriesMutableLiveData.setValue(task.getResult().toObjects(RestaurantCategory.class));
                    }
                });
    }

    public void filterRestaurants(String categories){
        restaurantsMutableLiveData.setValue(new ArrayList<>());

        Log.d(TAG, "filterRestaurants: "+categories);

        firestoreRepository.getRestaurants(categories).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        restaurantsMutableLiveData.setValue(task.getResult().toObjects(Restaurant.class));
                    }
                });
    }

    public LiveData<List<RestaurantCategory>> getRestaurantsCategoryLiveData(){
        return restaurantCategoriesMutableLiveData;
    }

    public LiveData<List<Restaurant>> getRestaurantsLiveData(){
        return restaurantsMutableLiveData;
    }

    //endregion

    //region Main menu view model
    private MutableLiveData<List<MainMenuElement>> mainMenuElements;

    public void initializeMainMenuViewModel(){
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
    private MutableLiveData<Boolean> areAllOperationsDoneMutableLiveData;

    public void initializeSuggestionsViewModel(){
        areAllOperationsDoneMutableLiveData = new MutableLiveData<>();
    }

    public UploadTask uploadSuggestionImage(Uri suggestionFileUri){
        areAllOperationsDoneMutableLiveData.setValue(false);
        return firestorageRepository.uploadSuggestionImage(suggestionFileUri);
    }

    public void uploadSuggestion(Suggestion suggestion){
        areAllOperationsDoneMutableLiveData.setValue(false);
        firestoreRepository.
                uploadSuggestion(suggestion).
                addOnCompleteListener(task -> areAllOperationsDoneMutableLiveData.setValue(true));
    }

    public void suggestionProcessed(){
        areAllOperationsDoneMutableLiveData.setValue(null);
    }

    public LiveData<Boolean> getAreAllOperationsDoneLiveData(){
        return areAllOperationsDoneMutableLiveData;
    }
    //endregion

    //region Comments view model
    public MutableLiveData<List<Comment>> commentsMutableLiveData;

    public void initializeComments(){
        commentsMutableLiveData = new MutableLiveData<>();
    }

    public Task<Void> postComment(String text, RecyclerViewElement element){
        Comment comment = new Comment();
        comment.setText(text);
        comment.setDate(new Date());
        comment.setUser(userRepository.getUserReference());

        return firestoreRepository.postComment(comment, element).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        userRepository.addComment(comment);

                        List<Comment> comments = commentsMutableLiveData.getValue();
                        comments.add(0, comment);

                        commentsMutableLiveData.setValue(comments);
                    }
                });
    }

    public LiveData<List<Comment>> getComments(RecyclerViewElement element){
        commentsMutableLiveData.setValue(null);

        firestoreRepository.getComments(element.toString()).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        commentsMutableLiveData.setValue(task.getResult().toObjects(Comment.class));
                    }
                });

        return commentsMutableLiveData;
    }
    //endregion

    public void getNearbyResources(){

    }
}



















