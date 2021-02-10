package com.craxiom.networksurvey.helpers.models.message;

import java.util.Objects;

public class WifiBeaconModel {
 private int id;
    private String geom;
    private int time;
    private int recordNumber;
    private String bssid;
    private String ssid;

    public int getId() {
        return id;
    }

    public String getGeom() {
        return geom;
    }

    public int getTime() {
        return time;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public String getBssid() {
        return bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public int getChannel() {
        return channel;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getCipherSuites() {
        return cipherSuites;
    }

    public String getAkmSuites() {
        return akmSuites;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public Boolean getWps() {
        return wps;
    }

    public Float getSignalStrength() {
        return signalStrength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiBeaconModel that = (WifiBeaconModel) o;
        return id == that.id &&
                time == that.time &&
                recordNumber == that.recordNumber &&
                channel == that.channel &&
                frequency == that.frequency &&
                Objects.equals(geom, that.geom) &&
                Objects.equals(bssid, that.bssid) &&
                Objects.equals(ssid, that.ssid) &&
                Objects.equals(cipherSuites, that.cipherSuites) &&
                Objects.equals(akmSuites, that.akmSuites) &&
                Objects.equals(encryptionType, that.encryptionType) &&
                Objects.equals(wps, that.wps) &&
                Objects.equals(signalStrength, that.signalStrength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, geom, time, recordNumber, bssid, ssid, channel, frequency, cipherSuites, akmSuites, encryptionType, wps, signalStrength);
    }

    private int channel;

    @Override
    public String toString() {
        return "WifiBeaconModel{" +
                "id=" + id +
                ", geom='" + geom + '\'' +
                ", time=" + time +
                ", recordNumber=" + recordNumber +
                ", bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", channel=" + channel +
                ", frequency=" + frequency +
                ", cipherSuites='" + cipherSuites + '\'' +
                ", akmSuites='" + akmSuites + '\'' +
                ", encryptionType='" + encryptionType + '\'' +
                ", wps=" + wps +
                ", signalStrength=" + signalStrength +
                '}';
    }

    private int frequency;
    private String cipherSuites;
    private String akmSuites;
    private String encryptionType;
    private Boolean wps;
    private Float signalStrength;

    public static final class WifiBeaconModelBuilder
    {
        private int id;

        public int getId()
        {
            return id;
        }

        public String getGeom()
        {
            return geom;
        }

        public int getTime()
        {
            return time;
        }

        public int getRecordNumber()
        {
            return recordNumber;
        }

        public String getBssid()
        {
            return bssid;
        }

        public String getSsid()
        {
            return ssid;
        }

        public int getChannel()
        {
            return channel;
        }

        public int getFrequency()
        {
            return frequency;
        }

        public String getCipherSuites()
        {
            return cipherSuites;
        }

        public String getAkmSuites()
        {
            return akmSuites;
        }

        private String geom;
        private int time;
        private int recordNumber;
        private String bssid;
        private String ssid;
        private int channel;
        private int frequency;
        private String cipherSuites;
        private String akmSuites;

        public String getEncryptionType() {
            return encryptionType;
        }

        private String encryptionType;
        private Boolean wps;
        private Float signalStrength;

        public Boolean getWps() {
            return wps;
        }

        public Float getSignalStrength() {
            return signalStrength;
        }

        public WifiBeaconModelBuilder()
        {
        }

        public static WifiBeaconModelBuilder aWifiBeaconModel()
        {
            return new WifiBeaconModelBuilder();
        }

        public WifiBeaconModelBuilder setId(int id)
        {
            this.id = id;
            return this;
        }

        public WifiBeaconModelBuilder setGeom(String geom)
        {
            this.geom = geom;
            return this;
        }

        public WifiBeaconModelBuilder setTime(int time)
        {
            this.time = time;
            return this;
        }

        public WifiBeaconModelBuilder setRecordNumber(int recordNumber)
        {
            this.recordNumber = recordNumber;
            return this;
        }

        public WifiBeaconModelBuilder setBssid(String bssid)
        {
            this.bssid = bssid;
            return this;
        }

        public WifiBeaconModelBuilder setSsid(String ssid)
        {
            this.ssid = ssid;
            return this;
        }

        public WifiBeaconModelBuilder setChannel(int channel)
        {
            this.channel = channel;
            return this;
        }

        public WifiBeaconModelBuilder setFrequency(int frequency)
        {
            this.frequency = frequency;
            return this;
        }

        public WifiBeaconModelBuilder setCipherSuites(String cipherSuites)
        {
            this.cipherSuites = cipherSuites;
            return this;
        }

        public WifiBeaconModelBuilder setAkmSuites(String akmSuites)
        {
            this.akmSuites = akmSuites;
            return this;
        }

        public WifiBeaconModelBuilder setEncryptionType(String encryptionType)
        {
            this.encryptionType = encryptionType;
            return this;
        }

        public WifiBeaconModelBuilder setWps(Boolean wps)
        {
            this.wps = wps;
            return this;
        }

        public WifiBeaconModelBuilder setSignalStrength(Float signalStrength)
        {
            this.signalStrength = signalStrength;
            return this;
        }

        public WifiBeaconModel build()
        {
            WifiBeaconModel WifiBeaconModel = new WifiBeaconModel();
            WifiBeaconModel.bssid = this.bssid;
            WifiBeaconModel.cipherSuites = this.cipherSuites;
            WifiBeaconModel.id = this.id;
            WifiBeaconModel.recordNumber = this.recordNumber;
            WifiBeaconModel.geom = this.geom;
            WifiBeaconModel.time = this.time;
            WifiBeaconModel.channel = this.channel;
            WifiBeaconModel.ssid = this.ssid;
            WifiBeaconModel.akmSuites = this.akmSuites;
            WifiBeaconModel.frequency = this.frequency;
            WifiBeaconModel.encryptionType = this.encryptionType;
            WifiBeaconModel.wps = this.wps;
            WifiBeaconModel.signalStrength = this.signalStrength;
            return WifiBeaconModel;
        }
    }
}
