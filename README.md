# LightDrive12SerialDrivers
Drivers for the Mach-Engineering LightDrive 12.
You can purchase it [here](https://www.andymark.com/products/lightdrive-12-led-controller?via=Z2lkOi8vYW5keW1hcmsvV29ya2FyZWE6OkNhdGFsb2c6OkNhdGVnb3J5LzViYjYxOTUzYmM2ZjZkNmRlMWU2YTA5Zg).

This referenced their provided manual [here](https://mach-engineering.com/products/LDRV-12/UserGuide.pdf)

The preferred method of controlling the device will be with serial communications, hence the name. Why? More RGB and faster response times.
We also could've used the CAN bus but I felt they didn't do a good job at communicating as to how the CAN bus works.
There are 5 places you can use as a serial port:
- kMXP 
- kOnboard 
- kUSB 
- kUSB1 
- kUSB2 