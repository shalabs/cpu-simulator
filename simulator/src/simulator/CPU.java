
package simulator;

import static simulator.Simulator.AR;
import static simulator.Simulator.addToReadyQ;
import static simulator.Simulator.SR;
import static simulator.Simulator.availableDevice;
import static simulator.Simulator.availableMemory;
import static simulator.Simulator.completeQ;
import static simulator.Simulator.currentTime;
import static simulator.Simulator.readyQ;

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

    
    public void runProcess(int currentTime) {
        processInCPU.setStartTime(currentTime);
        processInCPU.setRemainingTime(processInCPU.getOrginalBurstTime()-processInCPU.getAccuredTime());
        if(readyQ.isEmpty()){
            quantum=processInCPU.getBurstTime();
            //Update SR and AR
            SR=AR=0;
            
        }
        else{
            quantum=AR;
            //Update SR and AR
            SR-=processInCPU.getBurstTime();
            AR=SR/readyQ.size();
        }
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
