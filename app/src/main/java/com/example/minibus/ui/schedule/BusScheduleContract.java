package com.example.minibus.ui.schedule;

import android.content.DialogInterface;

import androidx.annotation.StringRes;

import com.example.minibus.data.network.pojo.city.BusStop;
import com.example.minibus.data.network.pojo.schedule.BusScheduleResponse;
import com.example.minibus.data.network.pojo.schedule.BusTrip;
import com.example.minibus.ui.base.Contract;

public interface BusScheduleContract {

    interface View extends Contract.View {

        void showFilter();
        void showDirectionSwapAnimation();
        void showJumpTopFab();
        void showOnboardingDialog(@StringRes int titleResId,
                                  @StringRes int msgResId,
                                  @StringRes int btnResId,
                                  DialogInterface.OnClickListener clickListener);
        void showRefresh();
        void showBusTripLoading();
        void hideBusTripLoading();
        void hideRefresh();
        void hideJumpTopFab();
        void toggleFilter();
        void updateProfileBadge();
        void setProfileBadge(int bookingsQuantity);
        void setBusScheduleData(BusScheduleResponse busSchedule);
        void setToolbarSubtitle(String text);
        void setToolbarSubtitle(@StringRes int resId);
        void setDepartureBusStop(String departureBusStop);
        void setArrivalBusStop(String arrivalBusStop);
        void setDirection(String departureBusStop, String destinationCity);
        void openProfile();
        void openLogin();
        void openDepartureBusStops();
        void openBusTripSummary(BusTrip busTrip, BusStop departureBusStop, String departureDate);
        void jumpTop();
        void finish();
    }

    interface Presenter<V extends BusScheduleContract.View> extends Contract.Presenter<V> {

        void onDepartureStopsButtonClick();
        void onBackPressed();
        void onStart(String departureDate);
        void onCreateProfileBadge();
        void onRedirectToLogin();
        void onRefresh(String departureDate);
        void onUserLoggedIn();
        void onUserLoggedOut();
        void onUserBookedBusTrip(String departureDate);
        void onUserBookingsUpdate();
        void onFilterCollapsed();
        void onFilterExpanded();
        void onDirectionSwapButtonClick(String departureDate);
        void onDateClick(String departureDate);
        void onArrivalBusStopChange(BusStop arrivalCityFinalBusStop, BusStop departureCityStartBusStop);
        void onDepartureBusStopChange(BusStop selectedDepartureBusStop, String departureDate);
        void onDepartureBusStopClick();
        void onArrivalBusStopClick();
        void onProfileIconClick();
        void onFilterFabClick();
        void onJumpTopFabClick();
        void onBusTripClick(String departureDate, int id, int pos);
    }
}
