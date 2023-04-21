/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorparttwo;

import static simulatorparttwo.SimulatorPartTwo.addToReadyQ;
import static simulatorparttwo.SimulatorPartTwo.availableDevice;
import static simulatorparttwo.SimulatorPartTwo.availableMemory;
import static simulatorparttwo.SimulatorPartTwo.completeQ;
import static simulatorparttwo.SimulatorPartTwo.currentTime;
import static simulatorparttwo.SimulatorPartTwo.readyQ;

public class CPU {
    int quantum;
    Process processInCPU;

    public CPU() {
        
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void setProcessInCPU(Process processInCPU) {
        this.processInCPU = processInCPU;
    }

    public int getQuantum() {
        return quantum;
    }

    public Process getProcessInCPU() {
        return processInCPU;
    }

    
 
        //for static round robin
        public void runProcess(int currentTime,int quantum) {
        processInCPU.setStartTime(currentTime);
        processInCPU.setRemainingTime(processInCPU.getOrginalBurstTime()-processInCPU.getAccuredTime());
       
        //if not terminated
        if (processInCPU.getBurstTime() >= quantum) {
            int bt=processInCPU.getBurstTime();
            processInCPU.setBurstTime(bt-quantum);
            int FinishTime = currentTime + quantum;
            processInCPU.setFinishTime(FinishTime);

        } 

        else {
            //if terminated, bt is less than quantum
            int FinishTime = processInCPU.getBurstTime() + currentTime;
            processInCPU.setBurstTime(0);
            processInCPU.setFinishTime(FinishTime);

        }
    
    }
      
      public void stopProcessInCPU(){
                  if (processInCPU != null) {
            processInCPU.setAccuredTime((processInCPU.getFinishTime() - processInCPU.getStartTime()));
            if (processInCPU.getBurstTime()==0) {
                availableMemory += processInCPU.getMemoryUnitsRequested();
                availableDevice += processInCPU.getDevicesRequested();
                processInCPU.setFinishTime(currentTime);
                completeQ.add(processInCPU);
                processInCPU=null;
            } 
        // the process has not finished  yet,move  into readyQ
            else {
                addToReadyQ(processInCPU);
                processInCPU=null;
            }

        }
      }
    
}
