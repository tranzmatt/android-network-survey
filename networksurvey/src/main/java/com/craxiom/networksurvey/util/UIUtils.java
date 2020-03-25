/*
 * Copyright (C) 2015-2018 University of South  Florida, Sean J. Barbeau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craxiom.networksurvey.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.craxiom.networksurvey.Application;
import com.craxiom.networksurvey.R;
import com.google.android.material.chip.Chip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.text.TextUtils.isEmpty;
import static com.craxiom.networksurvey.view.GnssSkyView.MAX_VALUE_CN0;
import static com.craxiom.networksurvey.view.GnssSkyView.MAX_VALUE_SNR;
import static com.craxiom.networksurvey.view.GnssSkyView.MIN_VALUE_CN0;
import static com.craxiom.networksurvey.view.GnssSkyView.MIN_VALUE_SNR;

/**
 * Utilities for processing user interface elements.
 * <p>
 * Originally from the GPS Test open source Android app.  https://github.com/barbeau/gpstest
 */

public class UIUtils
{
    public static final String COORDINATE_LATITUDE = "lat";
    public static final String COORDINATE_LONGITUDE = "lon";

    /**
     * Formats a view so it is ignored for accessible access
     */
    public static void setAccessibilityIgnore(View view)
    {
        view.setClickable(false);
        view.setFocusable(false);
        view.setContentDescription("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        }
    }

    /**
     * Converts screen dimension units from dp to pixels, based on algorithm defined in
     * http://developer.android.com/guide/practices/screens_support.html#dips-pels
     *
     * @param dp value in dp
     * @return value in pixels
     */
    public static int dpToPixels(Context context, float dp)
    {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Returns true if the activity is still active and dialogs can be managed (i.e., displayed
     * or dismissed), or false if it is not
     *
     * @param activity Activity to check for displaying/dismissing a dialog
     * @return true if the activity is still active and dialogs can be managed, or false if it is
     * not
     */
    public static boolean canManageDialog(Activity activity)
    {
        if (activity == null)
        {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return !activity.isFinishing() && !activity.isDestroyed();
        } else
        {
            return !activity.isFinishing();
        }
    }

    /**
     * Returns true if the fragment is attached to the activity, or false if it is not attached
     *
     * @param f fragment to be tested
     * @return true if the fragment is attached to the activity, or false if it is not attached
     */
    public static boolean isFragmentAttached(Fragment f)
    {
        return f.getActivity() != null && f.isAdded();
    }

    /**
     * Returns a human-readable description of the time-to-first-fix, such as "38 sec"
     *
     * @param ttff time-to-first fix, in milliseconds
     * @return a human-readable description of the time-to-first-fix, such as "38 sec"
     */
    public static String getTtffString(int ttff)
    {
        if (ttff == 0)
        {
            return "";
        } else
        {
            return TimeUnit.MILLISECONDS.toSeconds(ttff) + " sec";
        }
    }

    /**
     * Converts the provided SNR values to a left margin value (pixels) for the avg SNR indicator ImageViews in gps_sky_signal
     * Left margin range for the SNR indicator ImageViews in gps_sky_signal is determined by dimens.xml
     * cn0_meter_width (based on device screen width) and cn0_indicator_min_left_margin values.
     * <p>
     * This is effectively an affine transform - https://math.stackexchange.com/a/377174/554287.
     *
     * @param snr signal-to-noise ratio of the satellite in dB (from GpsSatellite)
     * @return left margin value in pixels for the SNR indicator ImageViews
     */
    public static int snrToIndicatorLeftMarginPx(float snr, int minIndicatorMarginPx, int maxIndicatorMarginPx)
    {
        return (int) MathUtils.mapToRange(snr, MIN_VALUE_SNR, MAX_VALUE_SNR, minIndicatorMarginPx, maxIndicatorMarginPx);
    }

    /**
     * Converts the provided SNR values to a left margin value (pixels) for the avg SNR TextViews in gps_sky_signal
     * Left margin range for the SNR indicator TextView in gps_sky_signal is determined by dimens.xml
     * cn0_meter_width (based on device screen width) and cn0_textview_min_left_margin values.
     * <p>
     * This is effectively an affine transform - https://math.stackexchange.com/a/377174/554287.
     *
     * @param snr signal-to-noise ratio of the satellite in dB (from GpsSatellite)
     * @return left margin value in dp for the SNR TextViews
     */
    public static int snrToTextViewLeftMarginPx(float snr, int minTextViewMarginPx, int maxTextViewMarginPx)
    {
        return (int) MathUtils.mapToRange(snr, MIN_VALUE_SNR, MAX_VALUE_SNR, minTextViewMarginPx, maxTextViewMarginPx);
    }

    /**
     * Converts the provided C/N0 values to a left margin value (dp) for the avg C/N0 indicator ImageViews in gps_sky_signal
     * Left margin range for the C/N0 indicator ImageViews in gps_sky_signal is determined by dimens.xml
     * cn0_meter_width (based on device screen width) and cn0_indicator_min_left_margin values.
     * <p>
     * This is effectively an affine transform - https://math.stackexchange.com/a/377174/554287.
     *
     * @param cn0 carrier-to-noise density at the antenna of the satellite in dB-Hz (from GnssStatus)
     * @return left margin value in dp for the C/N0 indicator ImageViews
     */
    public static int cn0ToIndicatorLeftMarginPx(float cn0, int minIndicatorMarginPx, int maxIndicatorMarginPx)
    {
        return (int) MathUtils.mapToRange(cn0, MIN_VALUE_CN0, MAX_VALUE_CN0, minIndicatorMarginPx, maxIndicatorMarginPx);
    }

    /**
     * Converts the provided C/N0 values to a left margin value (dp) for the avg C/N0 TextViews in gps_sky_signal
     * Left margin range for the C/N0 indicator TextView in gps_sky_signal is determined by dimens.xml
     * cn0_meter_width (based on device screen width) and cn0_textview_min_left_margin values.
     * <p>
     * This is effectively an affine transform - https://math.stackexchange.com/a/377174/554287.
     *
     * @param cn0 carrier-to-noise density at the antenna of the satellite in dB-Hz (from GnssStatus)
     * @return left margin value in dp for the C/N0 TextViews
     */
    public static int cn0ToTextViewLeftMarginPx(float cn0, int minTextViewMarginPx, int maxTextViewMarginPx)
    {
        return (int) MathUtils.mapToRange(cn0, MIN_VALUE_CN0, MAX_VALUE_CN0, minTextViewMarginPx, maxTextViewMarginPx);
    }

    /**
     * Sets the margins for a given view
     *
     * @param v View to set the margin for
     * @param l left margin, in pixels
     * @param t top margin, in pixels
     * @param r right margin, in pixels
     * @param b bottom margin, in pixels
     */
    public static void setMargins(View v, int l, int t, int r, int b)
    {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        p.setMargins(l, t, r, b);
        v.setLayoutParams(p);
    }

    /**
     * Returns the provided latitude or longitude value in Degrees Minutes Seconds (DMS) format
     *
     * @param coordinate latitude or longitude to convert to DMS format
     * @return the provided latitude or longitude value in Degrees Minutes Seconds (DMS) format
     */
    public static String getDMSFromLocation(Context context, double coordinate, String latOrLon)
    {
        BigDecimal loc = new BigDecimal(coordinate);
        BigDecimal degrees = loc.setScale(0, RoundingMode.DOWN);
        BigDecimal minTemp = loc.subtract(degrees).multiply((new BigDecimal(60))).abs();
        BigDecimal minutes = minTemp.setScale(0, RoundingMode.DOWN);
        BigDecimal seconds = minTemp.subtract(minutes).multiply(new BigDecimal(60)).setScale(2, RoundingMode.HALF_UP);

        String hemisphere;
        int output_string;
        if (latOrLon.equals(UIUtils.COORDINATE_LATITUDE))
        {
            hemisphere = (coordinate < 0 ? "S" : "N");
            output_string = R.string.gps_lat_dms_value;
        } else
        {
            hemisphere = (coordinate < 0 ? "W" : "E");
            output_string = R.string.gps_lon_dms_value;
        }

        return context.getString(output_string, hemisphere, degrees.abs().intValue(), minutes.intValue(), seconds.floatValue());
    }

    /**
     * Returns the provided latitude or longitude value in Decimal Degree Minutes (DDM) format
     *
     * @param coordinate latitude or longitude to convert to DDM format
     * @param latOrLon   lat or lon to format hemisphere
     * @return the provided latitude or longitude value in Decimal Degree Minutes (DDM) format
     */
    public static String getDDMFromLocation(Context context, double coordinate, String latOrLon)
    {
        BigDecimal loc = new BigDecimal(coordinate);
        BigDecimal degrees = loc.setScale(0, RoundingMode.DOWN);
        BigDecimal minutes = loc.subtract(degrees).multiply((new BigDecimal(60))).abs().setScale(3, RoundingMode.HALF_UP);
        String hemisphere;
        int output_string;
        if (latOrLon.equals(COORDINATE_LATITUDE))
        {
            hemisphere = (coordinate < 0 ? "S" : "N");
            output_string = R.string.gps_lat_ddm_value;
        } else
        {
            hemisphere = (coordinate < 0 ? "W" : "E");
            output_string = R.string.gps_lon_ddm_value;
        }
        return context.getString(output_string, hemisphere, degrees.abs().intValue(), minutes.floatValue());
    }

    /**
     * Converts the provide value in meters to the corresponding value in feet
     *
     * @param meters value in meters to convert to feet
     * @return the provided meters value converted to feet
     */
    public static double toFeet(double meters)
    {
        return meters * 1000d / 25.4d / 12d;
    }

    /**
     * Converts the provide value in meters per second to the corresponding value in kilometers per hour
     *
     * @param metersPerSecond value in meters per second to convert to kilometers per hour
     * @return the provided meters per second value converted to kilometers per hour
     */
    public static float toKilometersPerHour(float metersPerSecond)
    {
        return metersPerSecond * 3600f / 1000f;
    }

    /**
     * Converts the provide value in meters per second to the corresponding value in miles per hour
     *
     * @param metersPerSecond value in meters per second to convert to miles per hour
     * @return the provided meters per second value converted to miles per hour
     */
    public static float toMilesPerHour(float metersPerSecond)
    {
        return toKilometersPerHour(metersPerSecond) / 1.6093440f;
    }

    /**
     * Sets the vertical bias for a provided view that is within a ConstraintLayout
     *
     * @param view view within a ConstraintLayout
     * @param bias vertical bias to be used
     */
    public static void setVerticalBias(View view, float bias)
    {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.verticalBias = bias;
        view.setLayoutParams(params);
    }

    /**
     * Tests to see if the provided text latitude, longitude, and altitude values are valid, and if
     * not shows an error dialog and returns false, or if yes then returns true
     *
     * @param activity
     * @param lat      latitude to validate
     * @param lon      longitude to validate
     * @param alt      altitude to validate
     * @return true if the latitude, longitude, and latitude are valid, false if any of them are not
     */
    public static boolean isValidLocationWithErrorDialog(AppCompatActivity activity, String lat, String lon, String alt)
    {
        String dialogTitle = Application.get().getString(R.string.ground_truth_invalid_location_title);
        String dialogMessage;

        if (!LocationUtils.isValidLatitude(lat))
        {
            dialogMessage = Application.get().getString(R.string.ground_truth_invalid_lat);
            UIUtils.showLocationErrorDialog(activity, dialogTitle, dialogMessage);
            return false;
        }
        if (!LocationUtils.isValidLongitude(lon))
        {
            dialogMessage = Application.get().getString(R.string.ground_truth_invalid_long);
            UIUtils.showLocationErrorDialog(activity, dialogTitle, dialogMessage);
            return false;
        }
        if (!isEmpty(alt) && !LocationUtils.isValidAltitude(alt))
        {
            dialogMessage = Application.get().getString(R.string.ground_truth_invalid_alt);
            UIUtils.showLocationErrorDialog(activity, dialogTitle, dialogMessage);
            return false;
        }
        return true;
    }

    /**
     * Shows an error dialog for an incorrectly entered latitude, longitude, or altitude
     *
     * @param activity
     * @param title    title of the error dialog
     * @param message  message body of the error dialog
     */
    private static void showLocationErrorDialog(AppCompatActivity activity, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                })
                .create()
                .show();
    }

    public static Dialog createQrCodeDialog(AppCompatActivity activity)
    {
        View view = activity.getLayoutInflater().inflate(R.layout.qr_code_instructions, null);
        CheckBox neverShowDialog = view.findViewById(R.id.qr_code_never_show_again);

        neverShowDialog.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            // Save the preference
            PreferenceUtils.saveBoolean(Application.get().getString(R.string.pref_key_never_show_qr_code_instructions), isChecked);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(R.string.qr_code_instructions_title)
                .setCancelable(false)
                .setView(view)
                .setPositiveButton(R.string.ok,
                        (dialog, which) -> {
                            IOUtils.openQrCodeReader(activity);
                        }
                ).setNegativeButton(R.string.not_now,
                        (dialog, which) -> {
                            // No op
                        }
                );
        return builder.create();
    }

    /**
     * Returns the provided location based on the provided coordinate format, and sets the provided Views (locationValue, chips) accordingly if views are provided,
     * and returns the string value.
     *
     * @param location              location to be formatted
     * @param locationValue         View to be set with the selected coordinateFormat
     * @param includeAltitude       true if altitude should be included, false if it should not
     * @param chipDecimalDegrees    View to be set as checked if "dd" is the coordinateFormat
     * @param chipDMS               View to be set as checked if "dms" is the coordinateFormat
     * @param chipDegreesDecimalMin View to be set as checked if "ddm" is the coordinateFormat
     * @param coordinateFormat      dd, dms, or ddm
     * @return the provided location based on the provided coordinate format
     */
    public static String formatLocationForDisplay(Location location, TextView locationValue, boolean includeAltitude, Chip chipDecimalDegrees, Chip chipDMS, Chip chipDegreesDecimalMin, String coordinateFormat)
    {
        String formattedLocation;
        switch (coordinateFormat)
        {
            // Constants below must match string values in do_not_translate.xml
            case "dd":
                // Decimal degrees
                formattedLocation = IOUtils.createLocationShare(location, includeAltitude);
                if (chipDecimalDegrees != null)
                {
                    chipDecimalDegrees.setChecked(true);
                }
                break;
            case "dms":
                // Degrees minutes seconds
                formattedLocation = IOUtils.createLocationShare(UIUtils.getDMSFromLocation(Application.get(), location.getLatitude(), UIUtils.COORDINATE_LATITUDE),
                        UIUtils.getDMSFromLocation(Application.get(), location.getLongitude(), UIUtils.COORDINATE_LONGITUDE),
                        (location.hasAltitude() && includeAltitude) ? Double.toString(location.getAltitude()) : null);
                if (chipDMS != null)
                {
                    chipDMS.setChecked(true);
                }
                break;
            case "ddm":
                // Degrees decimal minutes
                formattedLocation = IOUtils.createLocationShare(UIUtils.getDDMFromLocation(Application.get(), location.getLatitude(), UIUtils.COORDINATE_LATITUDE),
                        UIUtils.getDDMFromLocation(Application.get(), location.getLongitude(), UIUtils.COORDINATE_LONGITUDE),
                        (location.hasAltitude() && includeAltitude) ? Double.toString(location.getAltitude()) : null);
                if (chipDegreesDecimalMin != null)
                {
                    chipDegreesDecimalMin.setChecked(true);
                }
                break;
            default:
                // Decimal degrees
                formattedLocation = IOUtils.createLocationShare(location, includeAltitude);
                if (chipDecimalDegrees != null)
                {
                    chipDecimalDegrees.setChecked(true);
                }
                break;
        }
        if (locationValue != null)
        {
            locationValue.setText(formattedLocation);
        }
        return formattedLocation;
    }

    /**
     * Resets the activity title so the locale is updated
     *
     * @param a the activity to reset the title for
     */
    public static void resetActivityTitle(Activity a)
    {
        try
        {
            ActivityInfo info = a.getPackageManager().getActivityInfo(a.getComponentName(), GET_META_DATA);
            if (info.labelRes != 0)
            {
                a.setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}