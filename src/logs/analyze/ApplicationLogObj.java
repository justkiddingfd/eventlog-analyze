/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.analyze;

/**
 *
 * @author 0x01
 */
public class ApplicationLogObj {
    
    private int eventID;
    private String eventType;
    private String eventTime;
    private String eventStrings;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public ApplicationLogObj(int eventID, String eventType, String eventTime, String eventStrings) {
        this.eventID = eventID;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.eventStrings = eventStrings;
    }

    
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventStrings() {
        return eventStrings;
    }

    public void setEventStrings(String eventStrings) {
        this.eventStrings = eventStrings;
    }
    
    
}
