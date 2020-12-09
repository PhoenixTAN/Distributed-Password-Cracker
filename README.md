# Distributed-Password-Cracker

## Instructions
Design a distributed system (similar to Hadoop), where a user submits the md5 hash of a 5-character password (a-z, A-Z) to the system using a web interface. 

The web interface with the help of worker nodes cracks the password by a brute force approach. Students need to implement the web-interface as well as the management service that will dedicate jobs to worker nodes. The web-interface and the management service can be on the same machine, but worker nodes need to be different machines. The systems should be scalable, i.e. you can add/remove workers on the fly. The management service should use the REST API or socket programming to communicate with the worker nodes.

Skills: CGI-bin, socket programming.

## File structures
- ManagementServer: spring boot project of the management server.
- WorkerServer: spring boot project of the worker nodes.
- dispatcher: java code to dispatch jobs to different workers according to the input number of workers.
- doc
    - Proposal.
    - Project ideas.
    - Report instructions.
    - Project report.
- geni-rspec
    

## How to run this program?




