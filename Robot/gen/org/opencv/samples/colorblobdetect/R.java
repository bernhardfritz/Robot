/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package org.opencv.samples.colorblobdetect;

public final class R {
    public static final class attr {
        /** <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>any</code></td><td>-1</td><td></td></tr>
<tr><td><code>back</code></td><td>99</td><td></td></tr>
<tr><td><code>front</code></td><td>98</td><td></td></tr>
</table>
         */
        public static final int camera_id=0x7f010001;
        /** <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int show_fps=0x7f010000;
    }
    public static final class dimen {
        /**  Default screen margins, per the Android Design guidelines. 

         Example customization of dimensions originally defined in res/values/dimens.xml
         (such as screen margins) for screens with more than 820dp of available width. This
         would include 7" and 10" devices in landscape (~960dp and ~1280dp respectively).
    
         */
        public static final int activity_horizontal_margin=0x7f060000;
        public static final int activity_vertical_margin=0x7f060001;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
        public static final int led_symbol=0x7f020001;
    }
    public static final class id {
        public static final int any=0x7f090000;
        public static final int back=0x7f090001;
        public static final int calcHomography=0x7f090006;
        public static final int color_blob_detection_activity_surface_view=0x7f090003;
        public static final int connect=0x7f090004;
        public static final int disconnect=0x7f090005;
        public static final int front=0x7f090002;
        public static final int taskAction=0x7f090007;
    }
    public static final class layout {
        public static final int color_blob_detection_surface_view=0x7f030000;
        public static final int main=0x7f030001;
    }
    public static final class menu {
        public static final int main=0x7f080000;
    }
    public static final class string {
        public static final int action_settings=0x7f050003;
        public static final int app_name=0x7f050001;
        public static final int hello=0x7f050000;
        public static final int hello_world=0x7f050002;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f070000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f070001;
    }
    public static final class xml {
        public static final int device_filter=0x7f040000;
    }
    public static final class styleable {
        /** Attributes that can be used with a CameraBridgeViewBase.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #CameraBridgeViewBase_camera_id org.opencv.samples.colorblobdetect:camera_id}</code></td><td></td></tr>
           <tr><td><code>{@link #CameraBridgeViewBase_show_fps org.opencv.samples.colorblobdetect:show_fps}</code></td><td></td></tr>
           </table>
           @see #CameraBridgeViewBase_camera_id
           @see #CameraBridgeViewBase_show_fps
         */
        public static final int[] CameraBridgeViewBase = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link org.opencv.samples.colorblobdetect.R.attr#camera_id}
          attribute's value can be found in the {@link #CameraBridgeViewBase} array.


          <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>any</code></td><td>-1</td><td></td></tr>
<tr><td><code>back</code></td><td>99</td><td></td></tr>
<tr><td><code>front</code></td><td>98</td><td></td></tr>
</table>
          @attr name org.opencv.samples.colorblobdetect:camera_id
        */
        public static final int CameraBridgeViewBase_camera_id = 1;
        /**
          <p>This symbol is the offset where the {@link org.opencv.samples.colorblobdetect.R.attr#show_fps}
          attribute's value can be found in the {@link #CameraBridgeViewBase} array.


          <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name org.opencv.samples.colorblobdetect:show_fps
        */
        public static final int CameraBridgeViewBase_show_fps = 0;
    };
}