package core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Random;


public final class CaptchaGenerator {
    //Use Algerian，remove 1,0,i,o
    private static final String GRAPH_BASE_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String SMS_BASE_CODES = "0123456789";
    private static Random random = new Random();


    /**
     * Generate captcha code via default base
     *
     * @param size captcha code size
     * @return captcha code
     */
    public static String generateCaptchaCode(int size) {
        return generateCaptchaCode(size, GRAPH_BASE_CODES);
    }

    public static String generateSmsCaptchaCode(int size) {
        return generateCaptchaCode(size, SMS_BASE_CODES);
    }

    /**
     * Generate captcha code
     *
     * @param size    captcha code size
     * @param sources base codes
     * @return captcha code
     */
    private static String generateCaptchaCode(int size, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = SMS_BASE_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder captchaCode = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            captchaCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return captchaCode.toString();
    }

    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Generate random captcha code file and return captcha code
     *
     * @param width      image width
     * @param high       image high
     * @param outputFile file directory
     * @param size       captcha code size
     * @return captcha code
     * @throws IOException I/O Exception
     */
    public static String outputCaptchaImage(int width, int high, File outputFile, int size) throws IOException {
        String captchaCode = generateCaptchaCode(size);
        outputFile = new File(outputFile, captchaCode + ".jpg");
        outputImage(width, high, outputFile, captchaCode);
        return captchaCode;
    }

    /**
     * Generate random captcha code output stream and return captcha code
     *
     * @param width image width
     * @param high  image high
     * @param os    output stream
     * @param size  captcha code size
     * @return captcha code
     * @throws IOException I/O Exception
     */
    public static String outputCaptchaImage(int width, int high, OutputStream os, int size) throws IOException {
        String captchaCode = generateCaptchaCode(size);
        outputImage(width, high, os, captchaCode);
        return captchaCode;
    }

    /**
     * Generate captcha code file via captcha code
     *
     * @param width       picture width
     * @param high        picture high
     * @param outputFile  output file
     * @param captchaCode captcha code
     * @throws IOException I/O Exception
     */
    public static void outputImage(int width, int high, File outputFile, String captchaCode) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(width, high, fos, captchaCode);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Generate captcha code output stream via captcha code
     *
     * @param width       picture width
     * @param high        picture high
     * @param os          output stream
     * @param captchaCode captcha code
     * @throws IOException I/O Exception
     */
    public static void outputImage(int width, int high, OutputStream os, String captchaCode) throws IOException {
        int size = captchaCode.length();
        BufferedImage image = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Set the border color
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, width, high);

        //Set back-ground color
        Color c = getRandColor(200, 250);
        g2.setColor(c);
        g2.fillRect(0, 2, width, high - 4);

        //draw interfering line
        Random random = new Random();
        //set line color
        g2.setColor(getRandColor(160, 200));
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(width - 1);
            int y1 = random.nextInt(high - 1);
            int x2 = random.nextInt(6) + 1;
            int y2 = random.nextInt(12) + 1;
            g2.drawLine(x1, y1, x1 + x2 + 40, y1 + y2 + 20);
        }

        //Add hot pixel
        float yawpRate = 0.05f;
        int area = (int) (yawpRate * width * high);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(high);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, width, high, c);

        g2.setColor(getRandColor(100, 160));
        int fontSize = high - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = captchaCode.toCharArray();
        for (int i = 0; i < size; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (width / size) * i
                    + fontSize / 2, high / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((width - 10) / size) * i + 5, high / 2 + fontSize / 2 - 13);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    public static String outputImageBase64(int width, int high, String captchaCode) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputImage(width, high, outputStream, captchaCode);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static String outputImageBase64(String captchaCode) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputImage(300, 80, outputStream, captchaCode);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int width, int high, Color color) {
        shearX(g, width, high, color);
        shearY(g, width, high, color);
    }

    private static void shearX(Graphics g, int width, int high, Color color) {

        int period = random.nextInt(2);

        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < high; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, width, 1, (int) d, 0);
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + width, i, width, i);
        }

    }

    private static void shearY(Graphics g, int width, int high, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        int frames = 20;
        int phase = 7;
        for (int i = 0; i < width; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, high, 0, (int) d);
            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + high, i, high);

        }
    }
}
