package com.example.minibus.ui.profile;

import com.example.minibus.data.network.pojo.booking.Booking;
import com.example.minibus.ui.base.Contract;

import java.util.List;

public interface UserProfileContract {

    interface View extends Contract.View {

        void setUserBookingsData(List<Booking> bookings);
        void updateUserBookingsBadge();
        void setUserData(String userName, String userPhone);
        void logout();
        void close();
    }

    interface Presenter<V extends UserProfileContract.View> extends Contract.Presenter<V> {

        void onStart();
        void onLogoutButtonClick();
        void onBookingCancelButtonClick(int bookingId);
        void onBusScheduleButtonClick();
    }
}
