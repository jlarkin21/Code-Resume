﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SharpPcap;

namespace MyPacketCapturer
{
    public partial class captureForm : Form
    {
        CaptureDeviceList devices; 
        public static ICaptureDevice device; //selected device out of device list
        public static string stringPackets = ""; //Data that is captured
        public static int numPackets = 0;
        sendForm fSend; //create class variable

        public captureForm()
        {
            InitializeComponent();
            //Get list of devices
            devices = CaptureDeviceList.Instance;

            //Check for at least one device
            if (devices.Count < 1)
            {
                MessageBox.Show("No Capture Devices Found.");
                Application.Exit();
            }

            //Add the devices to the combo box, show description
            cmbDevices.DataSource = devices;
            cmbDevices.DisplayMember = "Description";

            //Get the second device and display in combo box
            device = devices[2];
            cmbDevices.Text = device.Description;

            //Register our handler function to the 'packet arrival' event
            device.OnPacketArrival += new SharpPcap.PacketArrivalEventHandler(device_OnPacketArrival);
            
            //Open device for capturing
            int readTimeoutMilliseconds = 1000;
            device.Open(DeviceMode.Promiscuous, readTimeoutMilliseconds);
            
        }

        private static void device_OnPacketArrival(Object sender, CaptureEventArgs packet)
        {
            numPackets++;

            //Label packet number in capture window
            stringPackets += "Packet Number: " + Convert.ToString(numPackets) + Environment.NewLine;

            //Array to store all data from packet
            byte[] data = packet.Packet.Data;

            //Keep track of the number of bytes displayed per line
            int byteCounter = 0;

            stringPackets += "Destination MAC Address: ";
            //Process each byte in the captured packet
            foreach (byte b in data)
            {
                //Add byte to packet string (in hexidecimal)
                if (byteCounter <= 13)
                    stringPackets += b.ToString("X2") + " ";

                byteCounter++;

                switch (byteCounter)
                {
                    case 6: stringPackets += Environment.NewLine;
                        stringPackets += "Source MAC Address: ";
                        break;

                    case 12: stringPackets += Environment.NewLine;
                        stringPackets += "EtherType: ";
                        break;
                    
                    case 14: if(data[12] == 8) {
                        if(data[13] == 0) stringPackets += "(IP)";
                        if(data[13] == 6) stringPackets += "(ARP)";
                    }
                        
                    stringPackets += "(IP)";
                    break;
                }
            }

            stringPackets += Environment.NewLine;
            stringPackets += Environment.NewLine;

            byteCounter = 0;
            stringPackets += "Raw Data" + Environment.NewLine;
            


            //Process each byte in the captured packet
            foreach (byte b in data)
            {
                //Add byte to packet string (in hexidecimal)
                stringPackets += b.ToString("X2") + " ";
                byteCounter++;

                if (byteCounter == 16)
                {
                    byteCounter = 0;
                    stringPackets += Environment.NewLine;
                }
            }
            stringPackets += Environment.NewLine;
            stringPackets += Environment.NewLine;

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            txtCapturedData.AppendText(stringPackets);
            stringPackets = "";
            txtNumPackets.Text = Convert.ToString(numPackets);
        }

        private void btnStartStop_Click(object sender, EventArgs e)
        {
            try
            {
                if (btnStartStop.Text == "Start")
                {

                    device.StartCapture();
                    timer1.Enabled = true;
                    btnStartStop.Text = "Stop";
                }
                else
                {
                    device.StopCapture();
                    timer1.Enabled = false;
                    btnStartStop.Text = "Start";
                }
            }
            catch (Exception ex) { }
        }

        private void cmbDevices_SelectedIndexChanged(object sender, EventArgs e)
        {
            device = devices[cmbDevices.SelectedIndex];
            cmbDevices.Text = device.Description;
            txtGUID.Text = device.Name;

            //Register our handler function to the 'packet arrival' event
            device.OnPacketArrival += new SharpPcap.PacketArrivalEventHandler(device_OnPacketArrival);

            //Open device for capturing
            int readTimeoutMilliseconds = 1000;
            device.Open(DeviceMode.Promiscuous, readTimeoutMilliseconds);

        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            saveFileDialog1.Filter = "Text Files|*.txt|All Files|*.*";
            saveFileDialog1.Title = "Save the Captured Packets";
            saveFileDialog1.ShowDialog();

            //Check if a filename was given
            if(saveFileDialog1.FileName != ""){
                System.IO.File.WriteAllText(saveFileDialog1.FileName, txtCapturedData.Text);
            }
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            openFileDialog1.Filter = "Text Files|*.txt|All Files|*.*";
            openFileDialog1.Title = "Open the Captured Packets";
            openFileDialog1.ShowDialog();

            //Check if a filename was given
            if (openFileDialog1.FileName != "")
            {
                txtCapturedData.Text = System.IO.File.ReadAllText(openFileDialog1.FileName);
            }
        }

        private void clearToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (btnStartStop.Text == "Stop")
                MessageBox.Show("Stop capture first");
            else
            {
                txtNumPackets.Text = "";
                txtCapturedData.Text = "";
            }
        }

        private void sendWindowToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (sendForm.instantiations == 0)
            {
                fSend = new sendForm(); // create a new send form
                fSend.Show();
            }
        }
    }
}
