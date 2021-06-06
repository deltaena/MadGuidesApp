package com.example.madguidesapp.android.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.repository.FirestoreRepository;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityViewModel extends ViewModel {

    private FirestoreRepository firestoreRepository;

    private MutableLiveData<List<Resource>> resourcesMutableLiveData;

    public MapsActivityViewModel(){
        firestoreRepository = new FirestoreRepository();

        resourcesMutableLiveData = new MutableLiveData<>();
    }

    public void loadNearByResources(GeoLocation center, double radiusInM) {

        List<Task<QuerySnapshot>> tasks = firestoreRepository.getNearByResources(center, radiusInM);

        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(t -> {
                    List<Resource> matchingDocs = new ArrayList<>();

                    for (Task<QuerySnapshot> task : tasks) {
                        QuerySnapshot snap = task.getResult();
                        assert snap != null;
                        for (DocumentSnapshot doc : snap.getDocuments()) {
                            double lat = doc.getDouble("lat");
                            double lng = doc.getDouble("lng");

                            GeoLocation docLocation = new GeoLocation(lat, lng);
                            double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                            if (distanceInM <= radiusInM) {
                                matchingDocs.add(doc.toObject(Resource.class));
                            }
                        }
                    }
                    resourcesMutableLiveData.setValue(matchingDocs);
                });
    }

    public LiveData<List<Resource>> getResourcesLiveData(){
        return resourcesMutableLiveData;
    }
}




















