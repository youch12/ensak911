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
import com.ensak911.emplois_annonces.models.Annonce;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class AnnoncesRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int WITH = 1, WITHOUT = 0;

    private List<Annonce> annoncesList;
    Context contex;
    Activity myActivityReference;
    Annonce selectedAnnonce;


    public class ViewHolderWithImage extends RecyclerView.ViewHolder{
        public TextView dateCreation;
        public ImageButton annonceUpload, annonceShare;
        public ImageView annonceImage;

        public ViewHolderWithImage(View view) {
            super(view);
            dateCreation =(TextView) view.findViewById(R.id.annone_dateCreation);
            annonceImage =(ImageView) view.findViewById(R.id.annonce_image);
            annonceUpload=(ImageButton)view.findViewById(R.id.annonce_upload);
            annonceShare=(ImageButton)view.findViewById(R.id.annonce_share);


        }

    }
    public class ViewHolderWithoutImage extends RecyclerView.ViewHolder{
        public TextView dateCreation, content;
        public ImageButton annonceShare, annonceCopy;


        public ViewHolderWithoutImage(View view) {
            super(view);
            dateCreation =(TextView) view.findViewById(R.id.annone_without_dateCreation);
            content =(TextView) view.findViewById(R.id.annonce_without_contenu);
            annonceShare=(ImageButton)view.findViewById(R.id.annonce_without_share);
            annonceCopy=(ImageButton)view.findViewById(R.id.annonce_without_copy_content);


        }

    }


    public AnnoncesRecyclerView(Activity myActivityReference, Context context, List<Annonce> annonces) {
        this.annoncesList = annonces;
        this.myActivityReference=myActivityReference;
        this.contex=context;
    }

    @Override
    public int getItemViewType(int position) {
        if (annoncesList.get(position).getAttachement()!=null) {
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
                View v1 = inflater.inflate(R.layout.activity_annonces_list_item_view_with_image, viewGroup, false);
                viewHolder = new ViewHolderWithImage(v1);
                break;

            case WITHOUT:
                View v2 = inflater.inflate(R.layout.activity_annonces_list_item_view_without_image, viewGroup, false);
                viewHolder = new ViewHolderWithoutImage(v2);
                break;

            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
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
        final Annonce oneAnnonce=annoncesList.get(position);

        String timePassedString = DateUtils.getRelativeTimeSpanString(annoncesList.get(position).getDate().getTime(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.dateCreation.setText(timePassedString);
        viewHolder.content.setText(oneAnnonce.getContenu());


        viewHolder.annonceShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, oneAnnonce.getContenu());
                contex.startActivity(Intent.createChooser(sharingIntent,"Share using"));


            }
        });

        viewHolder.annonceCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) contex.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("annonce", oneAnnonce.getContenu());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(contex,contex.getResources().getString(R.string.copied_to_clipboard),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    void configureViewHolderWithImage(final ViewHolderWithImage viewHolder, int position)
    {
        final Annonce oneAnnonce=annoncesList.get(position);
        Picasso.with(contex).load(annoncesList.get(position).getAttachement()).into(viewHolder.annonceImage);
        String timePassedString = DateUtils.getRelativeTimeSpanString(annoncesList.get(position).getDate().getTime(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.dateCreation.setText(timePassedString);
        viewHolder.annonceShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(oneAnnonce.getAttachement(),contex);

                }
            });

        viewHolder.annonceUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAnnonce=oneAnnonce;
                if (ContextCompat.checkSelfPermission(myActivityReference,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(myActivityReference,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }else {
                    downloadImage(oneAnnonce.getAttachement(), contex);
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

                    downloadImage(selectedAnnonce.getAttachement(), contex);

                } else {
                    selectedAnnonce=null;
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

    public void addItem(Annonce item) {
        annoncesList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return annoncesList.size();
    }



}


