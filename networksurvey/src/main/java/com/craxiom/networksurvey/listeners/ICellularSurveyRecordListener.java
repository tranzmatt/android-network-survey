package com.craxiom.networksurvey.listeners;

import com.craxiom.messaging.CdmaRecord;
import com.craxiom.messaging.GsmRecord;
import com.craxiom.messaging.LteRecord;
import com.craxiom.messaging.NrRecord;
import com.craxiom.messaging.UmtsRecord;
import com.craxiom.networksurvey.model.CellularRecordWrapper;

import java.util.List;

/**
 * Listener interface for those interested in being notified when a new cellular Survey Record is ready.
 *
 * @since 0.0.4
 */
public interface ICellularSurveyRecordListener
{
    /**
     * Called when a new GSM Survey Record is ready.
     *
     * @param gsmRecord the GSM Record.
     * @since 0.0.5
     */
    void onGsmSurveyRecord(GsmRecord gsmRecord);

    /**
     * Called when a new CDMA Survey Record is ready.
     *
     * @param cdmaRecord the CDMA Record.
     * @since 0.0.5
     */
    void onCdmaSurveyRecord(CdmaRecord cdmaRecord);

    /**
     * Called when a new UMTS Survey Record is ready.
     *
     * @param umtsRecord the UMTS Record.
     * @since 0.0.5
     */
    void onUmtsSurveyRecord(UmtsRecord umtsRecord);

    /**
     * Called when a new LTE Survey Record is ready.
     *
     * @param lteRecord the LTE Record.
     */
    void onLteSurveyRecord(LteRecord lteRecord);

    /**
     * Called when a new NR Survey Record is ready.
     *
     * @param nrRecord the New Radio (5G) record.
     * @since 1.5.0
     */
    void onNrSurveyRecord(NrRecord nrRecord);

    /**
     * Called when a new batch of cellular survey records are ready.
     * <p>
     * This method SHOULD NOT be used in addition to the individual protocol listener methods. It contains the full
     * group (i.e. records with the same {@link com.craxiom.networksurvey.constants.CellularMessageConstants#GROUP_NUMBER_COLUMN})
     * associated with a single cellular scan (not really a scan, but an snapshot in time of what towers the phone can
     * see). The individual methods were used prior to this listener method being added and if a listener consumes from
     * both types of methods, then it will receive duplicate records.
     *
     * @param cellularGroup the next group/batch of cellular survey records.
     * @since 1.6.0
     */
    default void onCellularBatch(List<CellularRecordWrapper> cellularGroup)
    {
    }

    /**
     * Notification of the current Data and Voice network types. This method is called even when the values have not
     * changed.
     * <p>
     * See {@link com.craxiom.networksurvey.CalculationUtils#getNetworkType(int)} for the possible string values.
     *
     * @param dataNetworkType  The data network type (e.g. "LTE"), which might be different than the voice network type.
     * @param voiceNetworkType The voice network type (e.g. "LTE").
     * @since 1.6.0
     */
    default void onNetworkType(String dataNetworkType, String voiceNetworkType)
    {
    }
}
