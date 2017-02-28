package com.thinkmobiles.android.samples.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;

public final class FirebaseAuthManager {

    private final FirebaseAuth mAuth;
    private final FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser user;

    private AuthStateListener authStateListener;

    public FirebaseAuthManager() {
        super();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public final void onAuthStateChanged(final @NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser _user = firebaseAuth.getCurrentUser();

                if (_user != null) {
                    // User is signed in
                    user = _user;
                } else {
                    // User is signed out
                    user = null;
                }

                if (authStateListener != null) authStateListener.onAuthorized(_user != null);
            }
        };
    }

    public final void subscribe() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    public final void unSubscribe() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public final void createUserWithEmailAndPassword(final @NonNull String email, final @NonNull String password, final @Nullable OnCompleteListener<AuthResult> onCompleteListener) {
        final Task<AuthResult> resultTask = mAuth.createUserWithEmailAndPassword(email, password);
        if (onCompleteListener != null) {
            resultTask.addOnCompleteListener(onCompleteListener);
        }
    }

    public final void signInWithEmailAndPassword(final @NonNull String email, final @NonNull String password, final @Nullable OnCompleteListener<AuthResult> onCompleteListener) {
        final Task<AuthResult> resultTask = mAuth.signInWithEmailAndPassword(email, password);
        if (onCompleteListener != null) {
            resultTask.addOnCompleteListener(onCompleteListener);
        }
    }

    public final void signOut() {
        mAuth.signOut();
    }

    public final void setAuthStateListener(final AuthStateListener _authStateListener) {
        authStateListener = _authStateListener;
    }

    @Nullable
    public final FirebaseUser getUser() {
        return user;
    }

    public interface AuthStateListener {
        void onAuthorized(boolean isAuthorized);
    }
}
