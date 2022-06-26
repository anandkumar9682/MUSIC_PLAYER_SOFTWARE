import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSlider;
import javax.swing.JLabel;  
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File; 
import java.io.IOException; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

class AnandBind extends JFrame implements ActionListener ,ChangeListener {
    JFrame frame;
    JSlider slider;
    Long currentFrame=0l; 
    Clip clip;  
    String status="no"; 

    int skip;
    boolean selectFile=false;
    JPanel mainPanel;
    JTextField locationTF; 
    String defaultLocation="D:\\";

    AudioInputStream audioInputStream; 
  
    JButton playButton,stopButton,pauseButton,resumeButton,replayButton,selectLocationBTN;

    public AnandBind()
    {
        this.setTitle("Audio Player by Anand Bind");
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){ }

        mainPanel=new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(154, 171, 169));
        this.add(mainPanel);
        
        locationTF=new JTextField(defaultLocation);  
        locationTF.setBounds(15,15,200,22); 
        locationTF.setEditable(false);
        mainPanel.add(locationTF);
        
        selectLocationBTN=new JButton("Browse");
        selectLocationBTN.setFont(new Font("Arial",Font.BOLD,13));
        selectLocationBTN.setForeground(new Color(0,0,0));
        selectLocationBTN.setBackground(new Color(79, 97, 110));
        selectLocationBTN.setBounds(220,15,85,22);
        selectLocationBTN.addActionListener(this);
        mainPanel.add(selectLocationBTN);

        playButton=new JButton("Start");
        playButton.setFont(new Font("Arial",Font.BOLD,20));  
        playButton.setBounds(15,70,140,40); 
        playButton.setEnabled(false);
        mainPanel.add(playButton); 
        playButton.addActionListener(this);
        
        stopButton=new JButton("Stop");  
        stopButton.setEnabled(false);
        stopButton.setBounds(160,70,140,40);
        stopButton.setFont(new Font("Arial",Font.BOLD,20)); 
        mainPanel.add(stopButton); 
        stopButton.addActionListener(this);

        pauseButton=new JButton("pause");
        pauseButton.setEnabled(false); 
        pauseButton.setFont(new Font("Arial",Font.BOLD,20));  
        pauseButton.setBounds(15,120,140,40);
        mainPanel.add(pauseButton); 
        pauseButton.addActionListener(this);
        
        resumeButton=new JButton("Resume");  
        resumeButton.setBounds(160,120,140,40);
        resumeButton.setFont(new Font("Arial",Font.BOLD,20)); 
        resumeButton.setEnabled(false);
        mainPanel.add(resumeButton); 
        resumeButton.addActionListener(this);

        replayButton=new JButton("RePlay");
        replayButton.setEnabled(false);  
        replayButton.setBounds(15,180,285,40);
        replayButton.setFont(new Font("Arial",Font.BOLD,20)); 
        mainPanel.add(replayButton); 
        replayButton.addActionListener(this);

        JLabel createrName=new JLabel("Created By- Anand Bind");
        createrName.setBounds(40,235,250,20);
        createrName.setFont(new Font("Arial",Font.BOLD,20));
        createrName.setForeground(Color.red);
        mainPanel.add(createrName);


        JLabel n=new JLabel("Note: Player Support only wav File");
        n.setBounds(20,270,300,20);
        n.setFont(new Font("Arial",Font.BOLD,15));
        n.setForeground(Color.white);
        mainPanel.add(n);

        this.setVisible(true);
        this.setBounds(10,10,330,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    try
    {
        if(playButton==e.getSource())
        {
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(true);

            if(selectFile)
            {
                if (status.equals("play")) 
                JOptionPane.showMessageDialog(frame, "AlReady Play","Warning", JOptionPane.WARNING_MESSAGE);
                else
                {
                audioInputStream =  AudioSystem.getAudioInputStream(new File(defaultLocation).getAbsoluteFile()); 
                clip = AudioSystem.getClip(); 
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.setMicrosecondPosition(currentFrame); 
                clip.start(); 
                status = "play";
                }
            }
            else
                JOptionPane.showMessageDialog(frame, "Please Select music","Warning", JOptionPane.WARNING_MESSAGE);
        }

        else if(stopButton==e.getSource())
        {
            if (status.equals("stop")) 
                JOptionPane.showMessageDialog(frame, "Already stop","second string", JOptionPane.WARNING_MESSAGE);
            else
            {
                currentFrame = 0L; 
                clip.stop();  
                status="stop";
            }
        }
        else if(pauseButton==e.getSource())
        {
            if (status.equals("paused"))  
                JOptionPane.showMessageDialog(frame, "Already Pause","second string", JOptionPane.WARNING_MESSAGE); 
            else
            {
            currentFrame =  this.clip.getMicrosecondPosition(); 
            clip.stop(); 
            status = "paused";
            } 
        }
        else if(resumeButton==e.getSource())
        {
            if (status.equals("play"))  
                JOptionPane.showMessageDialog(frame, "Already Play","second string", JOptionPane.WARNING_MESSAGE); 
            else
            {
            audioInputStream = AudioSystem.getAudioInputStream( new File(defaultLocation).getAbsoluteFile()); 
            clip=AudioSystem.getClip();
            clip.open(audioInputStream); 
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
            clip.setMicrosecondPosition(currentFrame); 
            clip.start(); 
            status = "play"; 
            }
        }
    
        else if(replayButton==e.getSource())
        {
            clip.stop();        
            audioInputStream = AudioSystem.getAudioInputStream( new File(defaultLocation).getAbsoluteFile()); 
            clip = AudioSystem.getClip();
            clip.open(audioInputStream); 
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
            currentFrame = 0L; 
            clip.setMicrosecondPosition(0); 
            clip.start(); 
            status = "play";  
        }
        else if(selectLocationBTN==e.getSource())
        {   
            JFileChooser j = new JFileChooser(defaultLocation);
            j.setFileSelectionMode(JFileChooser.FILES_ONLY);
            j.setDialogTitle("Select Wav File"); 
            j.setAcceptAllFileFilterUsed(false); 
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Select only wav file", "wav"); 
            j.addChoosableFileFilter(restrict); 
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) 
            {   
                playButton.setEnabled(true);
                replayButton.setEnabled(true);
                selectFile=true;
                defaultLocation=j.getSelectedFile().getAbsolutePath();
                locationTF.setText(defaultLocation);
                try
                {
                    audioInputStream = AudioSystem.getAudioInputStream( new File(defaultLocation)); 
                    clip = AudioSystem.getClip();
                    long i=clip.getMicrosecondLength(); 
                    int k=(int)i/1000000;
                    slider = new JSlider(0, k, 0);
                    slider.addChangeListener(this);
                    slider.setBounds(15,235,285,25);
                    mainPanel.add(slider);
                    // sliderMy s= new sliderMy(slider,clip);
                    //s.start();
                }
                catch(Exception e3){ }
            } 
            else
                JOptionPane.showMessageDialog(this,"No File Select");
        }
    }    
    catch(Exception e1)
    {
        JOptionPane.showMessageDialog(frame, "File Not Support","second string", JOptionPane.WARNING_MESSAGE);
    }
    }
    @Override
    public void stateChanged(ChangeEvent ce)
    { 
        skip = slider.getValue();
        long f=skip*1000000; 
        if (f > 0 && f < clip.getMicrosecondLength())  
        { 
           currentFrame=f;  
        }
    }   
    public static void main(String[] args) 
    {
        new AnandBind();
    }
}
class sliderMy extends Thread
{
    JSlider slider;
    Clip clip;
    public sliderMy(JSlider slider1 , Clip clip1)
    {
        slider=slider1;
        clip=clip1;
    }
    @Override
    public void run()
    {
        while(true)
        {
            long temp = clip.getMicrosecondPosition();
            slider.setValue((int)temp/100);
        }
    }
}



