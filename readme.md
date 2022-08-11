Project - Current Status:
HomeActivity -> Contains remote buttons for Pensonic LED-2424 & LED-2224, 
can only add new views, but doesn't work, 
Edit NEC IR values in Pensonic class instead

SleepSchedulerActivity -> Fixed code only works for Pensonic TV Model 2424
----------------------------------------------------------------
For Java and Kivy-Plyer Developers who have a hard time implementing 
transmit(freq,pattern) method from class ConsumerIRManagers (Java) 
or IRBlaster (Python) as there is a limited resources on how to make 
it work and yet available resources are super complicated.

You can just take a look at my RemoteButton class located in src files.
You need a tested and working NEC Code (there are IR Finder apps available to
know what codes work) or the hard way (use this project's backend to make your 
own IR Finder).

NEC IR Code has 2 parts DEVICE ADDRESS (example, "00FE") and COMMAND CODE (example, "50AF").
You pass these two parameters to my RemoteButton class NEC converter then it will
return int[] pattern you needed for IR transmit() method.
If you happen to have only a half of the command code. 
Calculate the complement of that such that its sum is FF.
Example, given is 50, then FF minus 50 = AF.
For most device address, parts are complementary (sum=FF) but some are exceptions
For ex. our TV has device address 00FE yet 00+FE != FF

The projects remote frequency is in 38222 Hz skl you can edit it to 38000 etc. but 38222 is optimal freq.

GOOD REFERENCES FOR LEARNING:
https://techdocs.altium.com/display/FPGA/NEC+Infrared+Transmission+Protocol
- the CONSTANT lead-in of 0158 and 00AC, each multiplied by 1,000,000/freq yields to convert it to microseconds we need in int[] pattern
- a 9000 microsecond pulse and 4500 microsecond rest as it is stated in the reference (but in millisecond format)
- the CONSTANT lead out is 0015 and 38A4 yields decimal 549 (for Hex 0015 x Dec 1,000,000/38222) near the reference's
- target value 562 and an optional rest of 379,362 microseconds from (Hex 38A4 x Dec 1,000,000/38222). The reason 549 is still accepted by 
  IR receivers is because it tolerates little offsets +/-100?.
- For the important middle part between lead in and lead out, for ex.
- you have 01FE C03F as device addr and cmd code

- 2 ways that it can be sent to IR receiver as 

Not Reverse: (This works in my case)
- 00000001 11111110
- 11000000 00111111

Reverse (based on resources but didnt work for me, maybe because I use LeftPadding for both ways
you can try by setting isReverse to true in the RemoteButton class, )
- 10000000 01111111  
- 00000011 11111100 


- but each bits (binary digits) is translated to pairs of microseconds in the int[] array rawIRpattern.
- if 0 then its 562 562
- if 1 then its 562 1687
-                ^    ^
-               pulse,rest in microseconds

So overall, leadin + NecessaryIR + leadout looks like int[] a = {9000,4500,562,562,562, 562, 1687,...,562,1687,562,379362}
http://www.remotecentral.com/features/irdisp1.htm // page 1
http://www.remotecentral.com/features/irdisp4.htm // page 4 about NEC IR format
- This also contains a good explanation in a not-so complicated language, it has 4 pages don't overlook the other pages. 

