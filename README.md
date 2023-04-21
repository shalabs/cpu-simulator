# cpu-simulator
implementation of a program that simulates some of the job scheduling and CPU scheduling of an operating system. 

A graphic view of the simulator

<img width="872" alt="image" src="https://user-images.githubusercontent.com/80707214/233726628-d9c1eaae-e10b-4af2-a650-85b0c6b67c65.png">

When a job arrives, one of three things may happen:

1. If there is not enough total main memory or total number of devices in the system for the job, the job
is rejected never gets to one of the Hold Queues.
2. If there is not enough available main memory or available devices for the job, the job is put in one of
the Hold Queues, based on its priority, to wait for enough available main memory (Preallocation).
3. If there is enough main memory and devices for the job, then a process is created for the job, the required
main memory and devices are allocated to the process, and the process is put in the Ready Queue.
When a job terminates, the job releases any main memory. The release of main memory may cause one or more jobs to leave one of the Hold Queues and move to the Ready Queue.
Assume that all the two Hold Queues are based on priority. There are two external priorities: 1, and 2 with 1 being the highest priority. Priority is only used for the job scheduler:
--Job scheduling for Hold Queue 1 is Shortest Job First (SJF). 
--Job scheduling for Hold Queues 2 is First In First Out (FIFO).
Process scheduling will be Limited Round Robin. Once a job has exceeded the threshold of CPU time, it is considered a long job and may not run until the Ready Queue is empty. At that time the Long Queue becomes the Ready Queue and Round Robin (with a quantum) process scheduling is used.
