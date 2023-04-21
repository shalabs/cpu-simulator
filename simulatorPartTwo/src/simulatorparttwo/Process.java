/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorparttwo;

public class Process {
        int arrivalTime;
    int jobNumber;
    int memoryUnitsRequested;
    int devicesRequested;
    int burstTime;
    int priority;
    int startTime;
    int orginalBurstTime;
    int finishTime;
    int turnAroundTime;
    int accurdTime;
    int remainingTime;
    int waitingTime;
    
    
    public Process(int arrivalTime, int jobNumber, int memoryUnitsRequested, int devicesRequested, int burstTime, int priority) {
        this.arrivalTime = arrivalTime;
        this.jobNumber = jobNumber;
        this.memoryUnitsRequested = memoryUnitsRequested;
        this.devicesRequested = devicesRequested;
        this.burstTime = burstTime;
        this.priority = priority;
        waitingTime=0;
        
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setMemoryUnitsRequested(int memoryUnitsRequested) {
        this.memoryUnitsRequested = memoryUnitsRequested;
    }

    public void setDevicesRequested(int devicesRequested) {
        this.devicesRequested = devicesRequested;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public int getMemoryUnitsRequested() {
        return memoryUnitsRequested;
    }

    public int getDevicesRequested() {
        return devicesRequested;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getTurnAroundTime() {
        turnAroundTime=finishTime-arrivalTime;
        return turnAroundTime;
    }

    public int getOrginalBurstTime() {
        return orginalBurstTime;
    }

    public int getAccuredTime() {
        return accurdTime;
    }

    public void setOrginalBurstTime(int orginalBurstTime) {
        this.orginalBurstTime = orginalBurstTime;
    }

    public void setAccuredTime(int accurdTime) {
        this.accurdTime = accurdTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    public void addWaitingTime(int val){
        waitingTime+=val;
        
    }

    public int getWaitingTime() {
        return waitingTime;
    }
    
    
    
}
