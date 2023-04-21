/*
 Shahad Saeed 
Asma
Wejdan
Nada
Netbeans 8.0.2
Java
macOS Seirra
 */
package simulatorparttwo;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class SimulatorPartTwo {
           public static Process[] processTable =new Process[100] ;
       public static Queue<Process> submitQ=new LinkedList<Process>();
       public static  LinkedList<Process> hold1=new LinkedList<Process>();
       public static  LinkedList<Process> hold2=new LinkedList<Process>();
       public static  LinkedList<Process> readyQ=new LinkedList<Process>();
       public static LinkedList<Process> completeQ=new LinkedList<Process>();
       
       public static int readyQsize;
       public static int sumOfBursts;
       public static int averageOfBursts;
       public static int currentTime;
       public static long startTime;
       public static int mainMemory;
       public static int availableMemory;
       public static int serialDevices;
       public static int availableDevice;
       public static int processCounter;
       public static int finalDisplayTime;
       public static int quantum=11; //quantum = 10+ group number (1)
       public static CPU cpu=new CPU();
       
 public static void main(String[] args) throws Exception{
        //system configuration 

        int jobNumber;
        int arrivalTime;
        int requiredMemory;
        int requiredDevices;
        int burstTime;
        int priority;
        int externalEventTime;
        
        
        //create file object
        File inputFile = new File("input1.txt");
        if (!inputFile.exists()) {
            System.out.println("file soes not exist");
            System.exit(0);
        }// check if file exists or not .
        // read from input file , write in outputFile .  
        Scanner input = new Scanner(inputFile);
        File outputFile = new File("outputFile1.txt");
        PrintWriter output = new PrintWriter(outputFile);
    
        while (input.hasNext()){
            //C num M=num S=num
            //SR=AR=0;        
            processCounter=0;
            String c=input.next();
            startTime=Integer.parseInt(input.next());
            mainMemory=Integer.parseInt(input.next().substring(2));
            serialDevices=Integer.parseInt(input.next().substring(2));
            availableMemory=mainMemory;
            availableDevice=serialDevices;
            
            
           while(input.hasNext()){
               char character=input.next().charAt(0);
               if(character=='A'){
                   //A num J=num M=num S=num R=num P=num
                   arrivalTime=Integer.parseInt(input.next());
                   jobNumber=Integer.parseInt(input.next().substring(2));
                   requiredMemory=Integer.parseInt(input.next().substring(2));
                   requiredDevices=Integer.parseInt(input.next().substring(2));
                   burstTime=Integer.parseInt(input.next().substring(2));
                   priority=Integer.parseInt(input.next().substring(2));
                   
                   // if the required memory and devices are less than the available ,process is allowed 
                   if(requiredMemory<=mainMemory&&requiredDevices<=serialDevices){
                   Process p=new Process(arrivalTime,jobNumber,requiredMemory,requiredDevices,burstTime,priority);
                   int val=burstTime;
                   p.setOrginalBurstTime(val);
                   submitQ.add(p); 
                   processTable[processCounter]=p;
                   processCounter++;
                   }
               }
               else if(character=='D'){
                   externalEventTime=input.nextInt();
                   if(externalEventTime<999999){
                       submitQ.add(new Process(externalEventTime,999999,999999,999999,999999,999999));
                   }
                   else{
                       finalDisplayTime=externalEventTime;
                       break;
                   }
               }}
           
               cpu.setProcessInCPU(submitQ.poll());
               currentTime=cpu.getProcessInCPU().arrivalTime;
               availableMemory=availableMemory-cpu.getProcessInCPU().memoryUnitsRequested;
               availableDevice=availableDevice-cpu.getProcessInCPU().devicesRequested;
               cpu.runProcess(currentTime,quantum);
               
               
            int i = 0;
            int e = 0;  
                do {
                //i value
                if (!(submitQ.isEmpty())) {
                    i = submitQ.peek().getArrivalTime();
                } 
                else {
                    i = 999999;
                }
                //e value, the next external event (processExecuting exiting cpu)
                if (!(cpu.getProcessInCPU() == null)) {
                    e = cpu.getProcessInCPU().getFinishTime();
                } 
                else {
                    e = 999999;
                }
                
                if(cpu.getProcessInCPU()!=null)
                //compare them to decide next event
                currentTime = Math.min(i, e);
                //i and e must be executed inorder
                // i represents the arrival of a job
                // e represents the finish time of the process in the CPU               
                if (i < e) {
                    //a job has arrived and must be submitted to one of the queues 
                    externalEvent(currentTime, output);
                } 
                //process in cpu has finished its quantum, check hold queues and execute the first process in readyQ(if not empty)
                else if (e < i) {
                    internalEvent(currentTime);
                } 
                else {
                    internalEvent(currentTime);
                    externalEvent(currentTime, output);
                }  
            } 
          while (completeQ.size() != processCounter);
                
            if (finalDisplayTime == 999999 && completeQ.size() == processCounter) {

                DisplayStatus(output);
            }
            //just to print out waiting time to solve the comparison
            int ar=0;
            for (int j = 0; j < completeQ.size(); j++) {
                System.out.println("Process : "+completeQ.get(j).getJobNumber()+" Waiting time : "+completeQ.get(j).getWaitingTime());
                ar+=completeQ.get(j).getWaitingTime();
            }
            int av=ar/completeQ.size();
            System.out.println("average waiting time="+av+"\n");
            completeQ.clear();               
               
           
            
        }
        input.close();
        output.close();  
}
    
    
    public static void externalEvent(int currentTime, PrintWriter Output){

       if (!(submitQ.isEmpty())){
                  if (submitQ.peek().getJobNumber() == 999999) {
                     if (submitQ.poll().getArrivalTime() < 999999) {
                       DisplayStatus(currentTime, Output);
                }

            } 
            else { 
                      Process p=submitQ.poll();
                if (p.getMemoryUnitsRequested() <= availableMemory&&p.getDevicesRequested()<=availableDevice) {
                    availableMemory -= p.getMemoryUnitsRequested();
                    availableDevice -=p.getDevicesRequested();
                    p.setRemainingTime(p.getBurstTime());
                        addToReadyQ(p);

                } 
                  else {
                    addToHold(p);
                }
            }
      }
       }
    
    public static void internalEvent(int time){
          cpu.stopProcessInCPU();
          cpu.setProcessInCPU(null);
        pollFromHold();
        if (!(readyQ.isEmpty())) {
            Process p=readyQ.poll();
            p.addWaitingTime(currentTime-p.getStartTime());
            cpu.setProcessInCPU(p);
            int quantum=11; //quantum =10 + Group number (1)

            cpu.runProcess(currentTime,quantum);
        } 
    }
        public static void pollFromHold() {
                
        int size = hold1.size();
        int count = 0;
        // if the Hold queue not empty 
        while (count < size) {
            Process p = hold1.remove();
            if (p.getMemoryUnitsRequested() <= availableMemory && p.getDevicesRequested() <= availableDevice) {

                availableMemory -= p.getMemoryUnitsRequested();
                availableDevice -= p.getDevicesRequested();
                p.addWaitingTime(currentTime-p.getStartTime());
                addToReadyQ(p);

            } 
            else {
                hold1.add(p);
            }
            count++;
        } 

        //after finishing holeQ1, check holdQ2
        size = hold2.size();
        count = 0;
        while (count < size) {
            Process p = hold2.remove();
        if (p.getMemoryUnitsRequested() <= availableMemory && p.getDevicesRequested() <= availableDevice) {

                availableMemory -= p.getMemoryUnitsRequested();
                availableDevice -= p.getDevicesRequested();
                p.addWaitingTime(currentTime-p.getStartTime());
                addToReadyQ(p);

            } 
        
           else {
                hold2.add(p);
            }
            count++;
        } 


                }
        

        public static void addToHold(Process p){
        if(p.getPriority()==1){
            addHold1(p);
        }
        else{
            addHold2(p);
        }
    }
        
            
    //priority based on least amount of memory requested    
    public static void addHold1(Process p){
        if (hold1.isEmpty()||hold1.peek().getMemoryUnitsRequested()>p.memoryUnitsRequested){
            hold1.addFirst(p);
            //to calculate waiting time
            p.setStartTime(currentTime);            
        }
        
        else{
        int i=0;
        int j;
            for ( j = 0; j < hold1.size(); j++) {
                if(hold1.get(i).getMemoryUnitsRequested()>p.getMemoryUnitsRequested()){
                    break;
                }
            }
            //to calculate waiting time
            p.setStartTime(currentTime);
            hold1.add(j,p); 

    }}
    
    //FCFS
    public static void addHold2(Process p){
        hold2.add(p);
    }
    
        //Dynamic Round Robin
        public static void addToReadyQ(Process p){
            readyQ.add(p);
            //to calculate waiting time
            p.setStartTime(currentTime);
            }

    
        public static void DisplayStatus(PrintWriter output) {
                 
        output.println("<< Final state of system: ");
        output.println("  Current Available Main Memory =" + availableMemory + " ");
        output.println("  Current Devices = " + availableDevice + "");
        output.println("\n  Completed jobs:   \n----------------\n");
        output.println("  Job ID   Arrival Time    Finish Time  Turnaround Time  \n");
        output.println("  =================================================================\n");
        float sumOfTurnAroundTime=0;
        //to print processes by order        
        int index=0;
            for (int i = 1; i < completeQ.size()+25; i++) {
                     for (int j = 0; j < completeQ.size(); j++) {
                         if(completeQ.get(j).getJobNumber()==i){
                             index=j;
                               
                     sumOfTurnAroundTime+=completeQ.get(index).getTurnAroundTime();
                     output.printf("%5s %10s %15s %15s %n", completeQ.get(index).getJobNumber(), completeQ.get(index).getArrivalTime(), completeQ.get(index).getFinishTime(), completeQ.get(index).getTurnAroundTime());

                             break;
                         }
                     }
                   
            }
            float av=sumOfTurnAroundTime / completeQ.size();
        output.printf("\nSystem Turnaround Time =%.3f %n", av);
        output.println("\n\n****************************************************************************\n\n");
    
        } 
    
    
     public static void DisplayStatus(int time,PrintWriter output) {
        Process processExecuting=cpu.getProcessInCPU();
                 
        output.println("<< At time " + time + ": \n");
        output.println("  Current Available Main Memory = " + availableMemory + " ");
        output.println("  Current Devices               = " + availableDevice + "");
        output.println("\n  Completed jobs:   \n----------------\n");
        output.println("  Job ID   Arrival Time    Finish Time  Turnaround Time  \n");
        output.println("  =================================================================\n");
        float sumOfTurnAroundTime=0;
        //to print processes by order        
        int index=0;
            for (int i = 1; i < completeQ.size()+25; i++) {
                     for (int j = 0; j < completeQ.size(); j++) {
                         if(completeQ.get(j).getJobNumber()==i){
                             index=j;
                               
                     sumOfTurnAroundTime+=completeQ.get(index).getTurnAroundTime();
                     output.printf("%5s %10s %15s %15s %n", completeQ.get(index).getJobNumber(), completeQ.get(index).getArrivalTime(), completeQ.get(index).getFinishTime(), completeQ.get(index).getTurnAroundTime());

                             break;
                         }
                     }
                   
            }
            output.println("\n  Hold Queue 1:\n");
            output.println("  ----------------\n");
                for (int j = 0; j < hold1.size(); j++) {
                    output.print("   "+hold1.get(j).getJobNumber()+"     "); 
                }
            
            output.println("\n  Hold Queue 2: \n");
              output.println("  ----------------\n");
                     for (int j = 0; j < hold2.size(); j++) {
                    output.print("   "+hold2.get(j).getJobNumber()+"     "); 
                }
              
            output.println("\n\n  Ready Queue: \n"
                    + "  ----------------\n"
                    + "  JobID    RunTime    TimeAccrued\n"
                    + "  ===============================\n");
                   for (int j = 0; j < readyQ.size(); j++) {
                       output.printf("%5s %10s %10s %n",readyQ.get(j).getJobNumber(),readyQ.get(j).getBurstTime(),readyQ.get(j).getAccuredTime());
                }
            
              output.println("\n\n  Process running on the CPU: \n"
                    + "  ----------------------------\n"
                    + "  Job ID   Run Time   Time Left");
               output.printf("%5s %10s %10s %n",processExecuting.getJobNumber(),processExecuting.getRemainingTime(),processExecuting.getRemainingTime());
    output.println("\n");
 
        }


    
 
    
}

