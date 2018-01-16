package co.techmagic.randd.presentation.ui.profile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.techmagic.randd.data.application.UserApp;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class ProfileViewModel extends ViewModel {

    private FirebaseDatabase firebaseDatabase;
    MutableLiveData<UserApp> userData = new MutableLiveData<>();

    public ProfileViewModel(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void getInfo() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase.getReference("users").child(id).addListenerForSingleValueEvent(valueEventListener); // calls only once
    }

    public void saveInfo(String firstName, String lastName) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final UserApp user = new UserApp(id, firstName, lastName);
        firebaseDatabase.getReference("users").child(id).setValue(user);
        firebaseDatabase.getReference("users").child(id).addValueEventListener(valueEventListener); // calls every time
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            UserApp userApp = dataSnapshot.getValue(UserApp.class);
            userData.postValue(userApp);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}