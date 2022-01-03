package com.example.mechware.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mechware.ProfileFragment.AccountFrag;
import com.example.mechware.ProfileFragment.OtherFrag;
import com.example.mechware.ProfileFragment.PasswordFrag;

import org.jetbrains.annotations.NotNull;

public class ProfileFragAdapter  extends FragmentStateAdapter {

    public ProfileFragAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new AccountFrag();
            case 1:
                return new OtherFrag();
            default:
                return new PasswordFrag();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
