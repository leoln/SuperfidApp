package br.com.bhl.superfid.model;

import java.io.Serializable;

/**
 * Created by hericlespontes on 14/10/17.
 */

public class Dispositivo implements Serializable {
    private String macAddress;
    private String ssId;
    private String password;

    public Dispositivo(){

    }

    public Dispositivo(String macAddress, String ssId, String password) {
        this.macAddress = macAddress;
        this.ssId = ssId;
        this.password = password;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSsId() {
        return ssId;
    }

    public void setSsId(String ssId) {
        this.ssId = ssId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
