using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MyPacketCapturer
{
    public partial class sendForm : Form
    {
        public static int instantiations = 0;

        public sendForm()
        {
            InitializeComponent();
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            openFileDialog1.Filter = "Text Files|*.txt|All Files|*.*";
            openFileDialog1.Title = "Open the Captured Packets";
            openFileDialog1.ShowDialog();

            //Check if a filename was given
            if (openFileDialog1.FileName != "")
            {
                txtSendData.Text = System.IO.File.ReadAllText(openFileDialog1.FileName);
            }
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            saveFileDialog1.Filter = "Text Files|*.txt|All Files|*.*";
            saveFileDialog1.Title = "Save the Captured Packets";
            saveFileDialog1.ShowDialog();

            //Check if a filename was given
            if (saveFileDialog1.FileName != "")
            {
                System.IO.File.WriteAllText(saveFileDialog1.FileName, txtSendData.Text);
            }
        }

        private void sendForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            instantiations++;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string stringBytes = "";
            //Get the hex values from the file 
            foreach (string s in txtSendData.Lines)
            {
                //Take out comments
                string[] noComments = s.Split('#');
                string s1 = noComments[0];
                stringBytes += s1 + Environment.NewLine;
            }

            //Put hex values into a byte array
            string[] sBytes = stringBytes.Split(new string[] { "\n", "\r\n", " " }, StringSplitOptions.RemoveEmptyEntries);

            //Change the strings into bytes
            byte[] packet = new byte[sBytes.Length];
            int i = 0;
            foreach (string s in sBytes)
            {
                packet[i] = Convert.ToByte(s, 16); i++;
            }

            //sending out the packet
            try
            {
                captureForm.device.SendPacket(packet);
            }
            catch (Exception ex)
            {

            }
        }
    }
}
