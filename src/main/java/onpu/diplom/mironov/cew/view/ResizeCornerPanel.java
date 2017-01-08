package onpu.diplom.mironov.cew.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 */
public class ResizeCornerPanel extends JPanel implements MouseListener, MouseMotionListener {

        private Polygon resizeCorner = new Polygon();
        private int offsetX;
        private int offsetY;
        private Dimension offsetSize;
        private Cursor resizeCursor = new Cursor(Cursor.SE_RESIZE_CURSOR);
        private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        public final int barHeight;

        public ResizeCornerPanel(int barHeight) {
            super();
            this.barHeight = barHeight;
            setPreferredSize(new Dimension(200, barHeight));
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        private void createResizeHandle() {
            resizeCorner.reset();
            resizeCorner.addPoint(getWidth() - 2, getHeight() - 2);
            resizeCorner.addPoint(getWidth() - this.barHeight, getHeight() - 2);
            resizeCorner.addPoint(getWidth() - 2, getHeight() - this.barHeight);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2;
            g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setColor(Color.red);
            createResizeHandle();
            g2.drawPolygon(resizeCorner);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                int width = (int) (this.offsetSize.getWidth() - this.offsetX + e.getXOnScreen());
                int height = (int) (this.offsetSize.getHeight() - this.offsetY + e.getYOnScreen());
                this.getRootPane().getParent().setSize(width, height);
                createResizeHandle();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (resizeCorner.contains(e.getX(), e.getY())) {
                setCursor(resizeCursor);
            } else {
                setCursor(defaultCursor);
            }
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (resizeCorner.contains(e.getX(), e.getY())) {
                this.offsetX = e.getXOnScreen();
                this.offsetY = e.getYOnScreen();
                this.offsetSize = this.getRootPane().getParent().getSize();
            }
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }
    }
