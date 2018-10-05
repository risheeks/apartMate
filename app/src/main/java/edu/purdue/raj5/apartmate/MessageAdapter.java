package edu.purdue.raj5.apartmate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/*
*   Adapter to maintain the list of ChatBubbles listview in the chat_activity.xml
*/
public class MessageAdapter extends ArrayAdapter<ChatBubble> {

    private Activity activity;
    private List<ChatBubble> messages;

    /*
    *   Contructor for the Message Adapter
    */
    public MessageAdapter(Activity context, int resource, List<ChatBubble> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }

    /*
    *   returns the view to display 
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatBubble ChatBubble = getItem(position);
        int viewType = getItemViewType(position);

//        if (ChatBubble.myMessage()) {
//            layoutResource = R.layout.right_chat_bubble;
//        } else {
        layoutResource = R.layout.left_chat_bubble;
//        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set sender name
        holder.sdr.setText(ChatBubble.getSender());
        //set message content
        holder.msg.setText(ChatBubble.getContent());

        return convertView;
    }

    /*
    *   returns the total number of view types. this value should never change
    *   at runtime. Value 1 is returned because of just the left view
    */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /*
    *   returns a value between 0 and (getViewTypeCount - 1)
    */
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    /*
    *   An object that holds the Textviews that will contain the sender email
    *   and the message content of each chat message to be displayed
    */
    private class ViewHolder {
        private TextView msg;
        private TextView sdr;
        public ViewHolder(View v) {
            sdr = (TextView) v.findViewById(R.id.txt_sdr);
            msg = (TextView) v.findViewById(R.id.txt_msg);
        }
    }
}