package com.thinkmobiles.android.samples.domain.modules;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.thinkmobiles.android.samples.data.FirebaseAuthManager;
import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.models.User;

public final class AuthModuleImpl implements IAuthModule {

    private final FirebaseAuthManager firebaseAuthManager;

    private Callback registerCallback;

    private Callback signInCallback;
    private Callback signOutCallback;

    public AuthModuleImpl(final FirebaseAuthManager _firebaseAuthManager) {
        firebaseAuthManager = _firebaseAuthManager;
    }

    private Credential credential;

    @Override
    public final void setCredential(final Credential _credential) {
        credential = _credential;
    }

    @Override
    public final Credential getCredential() {
        return credential;
    }

    @Override
    public final void start() {
        firebaseAuthManager.subscribe();
    }

    @Override
    public final void stop() {
        firebaseAuthManager.unSubscribe();
    }

    @Override
    public final void register(final String email, final String password, final Callback callback) {
        registerCallback = callback;

        // TODO: 23-Feb-17 validate email, pass;

        firebaseAuthManager.createUserWithEmailAndPassword(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final Exception exception = task.getException();
                printStackTrace(exception);
                if (registerCallback != null){
                    registerCallback.onComplete(task.isSuccessful(), exception != null ? exception.getMessage() : "");
                }
            }
        });
    }

    @Override
    public final void signIn(final String email, final String password, final Callback callback) {
        signInCallback = callback;

        // TODO: 23-Feb-17 validate email, pass;

        firebaseAuthManager.signInWithEmailAndPassword(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final Exception exception = task.getException();
                printStackTrace(exception);
                if (signInCallback != null) {
                    signInCallback.onComplete(task.isSuccessful(), exception != null ? exception.getMessage() : "");
                }
            }
        });
    }

    @Override
    public final void signOut(final Callback callback) {
        signOutCallback = callback;
        firebaseAuthManager.signOut();
        if (signOutCallback != null) signOutCallback.onComplete(true, "" /*todo*/);
    }

    @Override
    public final void setSessionListener(final @NonNull SessionCallback sessionCallback) {
        firebaseAuthManager.setAuthStateListener(new FirebaseAuthManager.AuthStateListener() {
            @Override
            public void onAuthorized(boolean isAuthorized) {
                sessionCallback.onAuthState(isAuthorized);
            }
        });
    }

    @Override
    public final User getUser() {
        final FirebaseUser firebaseUser = firebaseAuthManager.getUser();

        if(firebaseUser == null) return null;

        return new User(firebaseUser.getUid(), firebaseUser.getEmail());

    }

    private void printStackTrace(@Nullable final Exception exception) {
        if (exception != null) {
            try {
                throw exception;
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
