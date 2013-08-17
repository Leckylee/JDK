/*
 * @(#)ColorConvert.java	1.27 98/09/22
 *
 * Copyright 1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package demos.Colors;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import DemoSurface;
import DemoPanel;


/**
 * ColorConvertOp a ColorSpace.TYPE_RGB BufferedImage to a ColorSpace.CS_GRAY 
 * BufferedImage.
 */
public class ColorConvert extends DemoSurface {

    private static Image img;
    private static Color colors[] = { Color.red, Color.pink, Color.orange, 
            Color.yellow, Color.green, Color.magenta, Color.cyan, Color.blue};


    public ColorConvert() {
        setBackground(Color.white);
        img = getImage("clouds.jpg");
    }


    public void drawDemo(int w, int h, Graphics2D g2) {

        int iw = img.getWidth(this);
        int ih = img.getHeight(this);
        FontRenderContext frc = g2.getFontRenderContext();
        Font font = g2.getFont();
        g2.setColor(Color.black);
        TextLayout tl = new TextLayout("ColorConvertOp RGB->GRAY", font, frc);
        tl.draw(g2, (float) (w/2-tl.getBounds().getWidth()/2), 
                            tl.getAscent()+tl.getLeading());

        BufferedImage srcImg = 
            new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
        Graphics2D srcG = srcImg.createGraphics();
        RenderingHints rhs = g2.getRenderingHints();
        srcG.setRenderingHints(rhs);
        srcG.drawImage(img, 0, 0, null);

        String s = "JavaColor";
        Font f = new Font("serif", Font.BOLD, iw/6);
        tl = new TextLayout(s, f, frc);
        Rectangle2D tlb = tl.getBounds();
        char[] chars = s.toCharArray();
        float charWidth = 0.0f;
        int rw = iw/chars.length;
        int rh = ih/chars.length;
        for (int i = 0; i < chars.length; i++) {
            tl = new TextLayout(String.valueOf(chars[i]), f, frc);
            Shape shape = tl.getOutline(null);
            srcG.setColor(colors[i%colors.length]);
            tl.draw(srcG, (float) (iw/2-tlb.getWidth()/2+charWidth), 
                        (float) (ih/2+tlb.getHeight()/2));
            charWidth += (float) shape.getBounds().getWidth();
            srcG.fillRect(i*rw, ih-rh, rw, rh);
            srcG.setColor(colors[colors.length-1-i%colors.length]);
            srcG.fillRect(i*rw, 0, rw, rh);
        }

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp theOp = new ColorConvertOp(cs, rhs);

        BufferedImage dstImg = 
                new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
        theOp.filter(srcImg, dstImg);

        g2.drawImage(srcImg, 10, 20, w/2-20, h-30, null);
        g2.drawImage(dstImg, w/2+10, 20, w/2-20, h-30, null);
    }


    public static void main(String s[]) {
        Frame f = new Frame("Java2D Demo - ColorConvert");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.add("Center", new DemoPanel(new ColorConvert()));
        f.pack();
        f.setSize(new Dimension(400,300));
        f.show();
    }
}
