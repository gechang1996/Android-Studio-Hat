package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;


public class DeletingDlg extends DialogFragment {
    private AlertDialog dlg;

    private final static String ID = "id";
    /**
     * Set true if we want to cancel
     */
    private volatile boolean cancel = false;
    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * Id for the image we are loading
     */
    private String catId;

    /**
     * Name for the image
     */
    private String Name;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if (bundle != null) {
            catId = bundle.getString(ID);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Create the dialog box
        // Create a cloud object and get the XML

        String areYouSure = getString(R.string.delete_sure);
        String wenhao=getString(R.string.wenhao);
        // Set the title
        builder.setTitle(R.string.deleting);
        builder.setMessage(areYouSure+this.Name+wenhao);
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                delete();
            }
        });
        dlg = builder.create();
        return  dlg;
    }
    private void delete()
    {
        if (!(getActivity() instanceof HatterActivity)) {
            return;
        }
        final HatterActivity activity = (HatterActivity) getActivity();
        final HatterView view = (HatterView) activity.findViewById(R.id.hatterView);
        /*
         * Create a thread to load the hatting from the cloud
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.deleteFromCloud(catId);
                if(!ok) {
                    /*
                     * If we fail to save, display a toast
                     */
                    // Please fill this in...
                    // Error condition!
                    view.post(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), R.string.DeleteFail, Toast.LENGTH_SHORT).show();
                        }

                    });
//

                }
            }


        }
        ).start();
    }
}
