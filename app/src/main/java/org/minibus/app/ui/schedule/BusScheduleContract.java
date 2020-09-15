package org.minibus.app.ui.schedule;

import androidx.annotation.StringRes;

import org.minibus.app.data.network.pojo.city.City;
import org.minibus.app.data.network.pojo.route.Route;
import org.minibus.app.data.network.pojo.schedule.BusScheduleResponse;
import org.minibus.app.data.network.pojo.schedule.BusTrip;
import org.minibus.app.ui.base.Contract;

import java.util.List;

public interface BusScheduleContract {

    interface View extends Contract.View {

        void showFilter();
        void showSwapDirectionAnimation();
        void showJumpTopFab();
        void showLoadingDataDialog();
        void hideLoadingDataDialog();
        void showRefresh();
        void showBusTripLoading();
        void hideBusTripLoading();
        void hideRefresh();
        void hideJumpTopFab();
        void toggleFilter();
        void updateProfileBadge();
        void setProfileBadge(int bookingsQuantity);
        void setBusScheduleData(List<BusTrip> busTrips, Route route);
        void setDirectionDescription(String text);
        void setDirectionDescription(@StringRes int resId);
        void setDepartureCity(String departureBusStop);
        void setArrivalCity(String arrivalBusStop);
        void setDirection(String fromCity, String toCity);
        void setOperationalDays(List<Integer> daysOfWeek);
        void openProfile();
        void openLogin();
        void openDepartureCities();
        void openArrivalCities();
        void openBusTripSummary(BusTrip busTrip, City departureCity, String departureDate);
        void jumpTop();
        void finish();
    }

    interface Presenter<V extends BusScheduleContract.View> extends Contract.Presenter<V> {

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
        void onArrivalCityChange(City city, String departureDate);
        void onDepartureCityChange(City city, String departureDate);
        void onDepartureCityClick();
        void onArrivalCityClick();
        void onProfileIconClick();
        void onFilterFabClick();
        void onJumpTopFabClick();
        void onBusTripSelectButtonClick(String departureDate, long id, int pos, String routeId);
    }
}
