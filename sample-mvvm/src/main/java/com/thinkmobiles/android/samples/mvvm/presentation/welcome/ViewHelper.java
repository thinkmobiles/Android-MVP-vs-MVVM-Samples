package com.thinkmobiles.android.samples.mvvm.presentation.welcome;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainAuthBinding;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainWelcomeBinding;
import com.thinkmobiles.android.samples.mvvm.util.binding.ObservableFieldAlwaysSet;

public final class ViewHelper implements IWelcomeView {

    public final ObservableFieldAlwaysSet<User> user = new ObservableFieldAlwaysSet<>();

    private final IWelcomeViewModel viewModel;
    private final EditText etMessageCheckIn;

    public ViewHelper(final IWelcomeViewModel _viewModel, final ViewMainWelcomeBinding binding) {
        viewModel = _viewModel;
        binding.setHelper(this);
        etMessageCheckIn = binding.etMessageCheckIn;
    }

    @Override
    public void setUser(User _user) {
        user.set(_user);
    }

    public final View.OnClickListener clickSignOut = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.onSignOutClicked();
        }
    };

    public final View.OnClickListener clickPostIt = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            final String msg = etMessageCheckIn.getText().toString();

            if(msg.trim().isEmpty()){
                Toast.makeText(v.getContext(),"Empty text! Please, enter something.", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.onPostItClicked(msg);
        }
    };

    public final View.OnClickListener clickViewCheckIns = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.onViewCheckInsClicked();
        }
    };


}
