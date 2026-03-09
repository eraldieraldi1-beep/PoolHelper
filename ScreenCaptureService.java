import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenCaptureService {

    private Robot robot;
    private Timer timer;

    public ScreenCaptureService() throws AWTException {
        this.robot = new Robot();
        this.timer = new Timer();
    }

    public void startCapturing() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                captureScreen();
            }
        }, 0, 1000); // captures every second
    }

    public void stopCapturing() {
        timer.cancel();
    }

    private void captureScreen() {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

        // Save the current screen capture as a PNG file
        try {
            ImageIO.write(screenFullImage, "png", new File("screenshot.png"));
            System.out.println("A new screenshot is captured and saved!");
        } catch (IOException e) {
            System.err.println(e);
        }

        // Call ball position detection here
        detectBallPosition(screenFullImage);
    }

    private void detectBallPosition(BufferedImage image) {
        // Implement ball detection logic here
        // This could involve processing the image to find the position of the ball
        System.out.println("Detecting ball position...");
        // Placeholder for detection logic
    }

    public static void main(String[] args) {
        try {
            ScreenCaptureService captureService = new ScreenCaptureService();
            captureService.startCapturing();

            // Add a shutdown hook to stop capturing when the application exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> captureService.stopCapturing()));
        } catch (AWTException e) {
            System.err.println(e);
        }
    }
}