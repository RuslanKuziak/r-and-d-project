package co.techmagic.randd.presentation.ui.profile;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ruslankuziak on 1/15/18.
 */

public class ProfileModelFactory implements ViewModelProvider.Factory {

    private FirebaseDatabase firebaseDatabase;

    public ProfileModelFactory(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(firebaseDatabase);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}