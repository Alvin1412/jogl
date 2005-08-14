/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 * 
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

package net.java.games.jogl;

import java.util.*;

/** An Animator subclass which attempts to achieve a target
    frames-per-second rate to avoid using all CPU time. The target FPS
    is only an estimate and is not guaranteed. */

public class FPSAnimator extends Animator {
  private Timer timer;
  private int   fps;

  /** Creates an FPSAnimator with a given target frames-per-second value. */
  public FPSAnimator(int fps) {
    this(null, fps);
  }

  /** Creates an FPSAnimator with a given target frames-per-second
      value and an initial drawable to animate. */
  public FPSAnimator(GLAutoDrawable drawable, int fps) {
    this.fps = fps;
    if (drawable != null) {
      add(drawable);
    }
  }

  /** Starts this FPSAnimator. */
  public synchronized void start() {
    if (timer != null) {
      throw new GLException("Already started");
    }
    long delay = (long) (1000.0f / (float) fps);
    timer.schedule(new TimerTask() {
        public void run() {
          Iterator iter = drawableIterator();
          while (iter.hasNext()) {
            GLAutoDrawable drawable = (GLAutoDrawable) iter.next();
            drawable.display();
          }
        }
      }, 0, delay);
  }

  /** Stops this FPSAnimator. Due to the implementation of the
      FPSAnimator it is not guaranteed that the FPSAnimator will be
      completely stopped by the time this method returns. */
  public synchronized void stop() {
    if (timer == null) {
      throw new GLException("Already stopped");
    }
    timer.cancel();
    timer = null;
  }
}
