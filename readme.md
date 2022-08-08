Project is just about to create its user interface
Currently, only contains simple button that sends a sequence of 
command codes e.g. {MENU,RIGHT,RIGHT, PRESS ... ETC} to adjust 
the Pensonic Model LED-2224 TV's Offtime/sleep schedule so that you don't 
have to suffer the painstaking pressing of a physical buttons.


----------------------------------------------------------------
For Java and Kivy-Plyer Developers who have a hard time implementing 
transmit(freq,pattern) method from class ConsumerIRManagers (Java) 
or IRBlaster (Python) as there is a limited resources on how to make 
it work and yet available resources are super complicated.

You can just take a look at my RemoteButton class located in src files.
You need a tested and working NEC Code (there are IR Finder apps available to
know what codes work) or the hard way (use this project's backend to make your 
own IR Finder HAHA).

NEC Code has 2 parts DEVICE ADDRESS (e.g. "00FE") and COMMAND CODE (e.g. "50AF").
You pass these two arguments to my RemoteButton's NEC converter then it will
return int[] pattern you needed for IR transmit() method.
If you happen to have only a half of the command code. 
Calculate the complement of that such that its sum is FF.
Example, given is 50, then FF minus 50 = AF.
For most device address, parts are complementary (sum=FF) but some are exceptions
For ex. our TV has device address 00FE yet 00+FE != FF

The projects remote frequency is in 38222 Hz skl you can edit it.

GOOD REFERENCES FOR LEARNING:
https://techdocs.altium.com/display/FPGA/NEC+Infrared+Transmission+Protocol
- a lead-in of 0158 and 00AC, each multiplied by 1,000,000/freq yields
- a 9000 and 4500 as it is stated in the reference (but in millisecond format)
- the lead out is 0015 and 38A4 yields a pair of value near the reference's
- target value pair 562 and 1687. IR receiver tolerates a little offset.
- For the middle part, for ex.
- you have 01FE C03F as device addr and cmd code
- it is sent to IR receiver as 
- 10000000 11111110  
- 00000011 11110101 
- take note first parts, 01 of device addr and C0 of cmd code are in reverse order
- 
- but each bits is translated to pairs of milliseconds in the int[] array
- if 0 then its 562 562
- if 1 then its 562 1687

http://www.remotecentral.com/features/irdisp1.htm // page 1
http://www.remotecentral.com/features/irdisp4.htm // page 4 about NEC format
- This also contains a good explanation, it has 4 pages don't overlook the other pages. 

