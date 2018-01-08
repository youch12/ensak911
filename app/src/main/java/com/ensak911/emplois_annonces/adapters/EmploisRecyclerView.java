package com.ensak911.emplois_annonces.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak911.R;
import com.ensak911.emplois_annonces.models.Emploi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by hp on 1/7/2018.
 */
public class EmploisRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int WITH = 1, WITHOUT = 0;

    private List<Emploi> emploisList;
    Context contex;
    Activity myActivityReference;
    Emploi selectedEmploi;


    public class ViewHolderWithImage extends RecyclerView.ViewHolder{
        public TextView dateCreation;
        public ImageButton emploiUpload, emploiShare;
        public ImageView emploiImage;

        public ViewHolderWithImage(View view) {
            super(view);
            dateCreation =(TextView) view.findViewById(R.id.emploi_dateCreation);
            emploiImage =(ImageView) view.findViewById(R.id.emploi_image);
            emploiUpload=(ImageButton)view.findViewById(R.id.emploi_upload);
            emploiShare=(ImageButton)view.findViewById(R.id.emploi_share);


        }

    }
    public class ViewHolderWithoutImage extends RecyclerView.ViewHolder{
        public TextView dateCreation, content;
        public ImageButton emploiShare, emploiCopy;


        public ViewHolderWithoutImage(View view) {
            super(view);
            dateCreation =(TextView) view.findViewById(R.id.emploi_without_dateCreation);
            content =(TextView) view.findViewById(R.id.emploi_without_contenu);
            emploiShare=(ImageButton)view.findViewById(R.id.emploi_without_share);
            emploiCopy=(ImageButton)view.findViewById(R.id.emploi_without_copy_content);


        }

    }


    public EmploisRecyclerView(Activity myActivityReference, Context context, List<Emploi> emplois) {
        this.emploisList = emplois;
        this.myActivityReference=myActivityReference;
        this.contex=context;
    }

    @Override
    public int getItemViewType(int position) {
        if (emploisList.get(position).getAttachement()!=null) {
            return WITH;
        } else {
            return WITHOUT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(this.contex);

        switch (viewType) {
            case WITH:
                View v1 = inflater.inflate(R.layout.activity_emplois_list_item_view_with_image, viewGroup, false);
                viewHolder = new ViewHolderWithImage(v1);
                break;

            case WITHOUT:
                View v2 = inflater.inflate(R.layout.activity_emplois_list_item_view_without_image, viewGroup, false);
                viewHolder = new ViewHolderWithoutImage(v2);
                break;

            default:
                View v = inflater.inflate(R.layout.activity_emplois_list_item_view_with_image, viewGroup, false);
                viewHolder = new ViewHolderWithImage(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case WITH:
                ViewHolderWithImage vh1 = (ViewHolderWithImage) viewHolder;
                configureViewHolderWithImage(vh1, position);
                break;
            case WITHOUT:
                ViewHolderWithoutImage vh2 = (ViewHolderWithoutImage) viewHolder;
                configureViewHolderWithoutImage(vh2, position);
                break;

            default:
                ViewHolderWithImage vh3 = (ViewHolderWithImage) viewHolder;
                configureViewHolderWithImage(vh3, position);
                break;
        }
    }

    void configureViewHolderWithoutImage(final ViewHolderWithoutImage viewHolder, int position)
    {
        final Emploi oneEmploi=emploisList.get(position);

        String timePassedString = DateUtils.getRelativeTimeSpanString(emploisList.get(position).getDate().getTime(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.dateCreation.setText(timePassedString);
        viewHolder.content.setText(oneEmploi.getContenu());


        viewHolder.emploiShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, oneEmploi.getContenu());
                contex.startActivity(Intent.createChooser(sharingIntent,"Share using"));


            }
        });

        viewHolder.emploiCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) contex.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("annonce", oneEmploi.getContenu());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(contex,contex.getResources().getString(R.string.copied_to_clipboard),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    void configureViewHolderWithImage(final ViewHolderWithImage viewHolder, int position)
    {
        final Emploi oneEmploi=emploisList.get(position);
        Picasso.with(contex).load(emploisList.get(position).getAttachement()).into(viewHolder.emploiImage);
        String timePassedString = DateUtils.getRelativeTimeSpanString(emploisList.get(position).getDate().getTime(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.dateCreation.setText(timePassedString);
        final int positionValue=position;


        viewHolder.emploiShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(oneEmploi.getAttachement(),contex);

            }
        });

        viewHolder.emploiUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEmploi=oneEmploi;
                if (ContextCompat.checkSelfPermission(myActivityReference,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(myActivityReference,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }else {
                    downloadImage(oneEmploi.getAttachement(), contex);
                }
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    downloadImage(selectedEmploi.getAttachement(), contex);

                } else {
                    selectedEmploi=null;
                }
                return;
            }

        }
    }

    static public void downloadImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + "annonce_"+ UUID.randomUUID()+".png");
                try {
                    file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                    ostream.close();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://"
                                        + Environment.getExternalStorageDirectory())));
                    } else {
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                                Uri.parse("file://"
                                        + Environment.getExternalStorageDirectory())));
                    }
                    Toast.makeText(context,context.getResources().getString(R.string.donwload_image_message),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public void shareImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
    public void addItem(Emploi item) {
        emploisList.add(item);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return emploisList.size();
    }



}

