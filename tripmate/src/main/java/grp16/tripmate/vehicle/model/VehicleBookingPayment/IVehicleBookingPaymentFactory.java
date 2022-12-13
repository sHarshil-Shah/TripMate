package grp16.tripmate.vehicle.model.VehicleBookingPayment;

import grp16.tripmate.vehicle.database.VehicleBookingPayment.IVehicleBookingPaymentDatabase;
import grp16.tripmate.vehicle.database.VehicleBookingPayment.IVehicleBookingPaymentQueryBuilder;

public interface IVehicleBookingPaymentFactory
{
    VehicleBookingPayment getNewVehicleBookingPayment();

    IVehicleBookingPaymentDatabase getVehicleBookingPaymentDatabase();

    IVehicleBookingPaymentQueryBuilder getVehicleBookingPaymentQueryBuilder();
}