package com.jstudio.panionline.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jstudio.panionline.R;

import java.util.List;

public class ProductListResponse {

    /**
     * statusCode : true
     * message : Products Listed Successfully!
     * data : [{"_id":"5e95189222131e18700b5692","productId":102,"productName":"Kinley","productImageUrl":"https://www.coca-colaindia.com/content/dam/journey/in/en/private/our-brands/Kinley%20Water/India-utl.jpg","productDescription":"Coca-Cola's Kinley Water goes through a rigorous and intensive process of purification before it reaches the consumer. The consistency and purity are maintained through an over 10-step process, which includes disinfection, sand filteration, activated carbon filtration, 10 micron polishing filtration.","productPrice":40,"productLikes":5,"productReviews":2,"__v":0},{"_id":"5e9519924bb7352768fd6855","productId":103,"productName":"Bisleri","productImageUrl":"https://www.bisleri.com/themes/bisleri/assets/images/static/bisleri-consumer1.png","productDescription":"A symbol of goodness, trust and purity, Bisleri has been a household name for decades. A leader in its category, it is the most trusted brand of mineral water in India. Having a strong presence, with 125 operational plants (13 owned) and a strong distribution network of 3000 Distributors & 5000 Distribution trucks across India & neighbouring countries, Bisleri stands true to its promise of providing safe, pure & healthy mineral water to consumers for the last 50 years.","productPrice":35,"productLikes":2,"productReviews":5,"__v":0},{"_id":"5e951e564bb7352768fd6856","productId":104,"productName":"Bailley","productImageUrl":"https://www.parleagro.com/uploads/images/170234B_PARLEAGRO-20.jpg","productDescription":"Bailley has been the symbol of purity and is widely preferred as a source of safe drinking water. Packaged across India in 52 state-of-the-art bottling plants, Bailley caters to diverse consumer needs and occasions. It is available in various pack sizes like 200ml, 250ml, 300ml, 500ml, 1ltr, 1.5ltr and 2ltr PET, as well as 5ltr, 20ltr and 25ltr jar.","productPrice":40,"productLikes":1,"productReviews":2,"__v":0}]
     */
    @SerializedName("statusCode")
    @Expose
    private boolean statusCode;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<DataBean> data;

    public boolean isStatusCode() {
        return statusCode;
    }

    public void setStatusCode(boolean statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * _id : 5e95189222131e18700b5692
         * productId : 102
         * productName : Kinley
         * productImageUrl : https://www.coca-colaindia.com/content/dam/journey/in/en/private/our-brands/Kinley%20Water/India-utl.jpg
         * productDescription : Coca-Cola's Kinley Water goes through a rigorous and intensive process of purification before it reaches the consumer. The consistency and purity are maintained through an over 10-step process, which includes disinfection, sand filteration, activated carbon filtration, 10 micron polishing filtration.
         * productPrice : 40
         * productLikes : 5
         * productReviews : 2
         * __v : 0
         */

        private String _id;
        private int productId;
        private String productName;
        private String productImageUrl;
        private String productDescription;
        private int productPrice;
        private int productLikes;
        private int productReviews;

        protected DataBean(Parcel in) {
            _id = in.readString();
            productId = in.readInt();
            productName = in.readString();
            productImageUrl = in.readString();
            productDescription = in.readString();
            productPrice = in.readInt();
            productLikes = in.readInt();
            productReviews = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(_id);
            dest.writeInt(productId);
            dest.writeString(productName);
            dest.writeString(productImageUrl);
            dest.writeString(productDescription);
            dest.writeInt(productPrice);
            dest.writeInt(productLikes);
            dest.writeInt(productReviews);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImageUrl() {
            return productImageUrl;
        }

        public void setProductImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductLikes() {
            return productLikes;
        }

        public void setProductLikes(int productLikes) {
            this.productLikes = productLikes;
        }

        public int getProductReviews() {
            return productReviews;
        }

        public void setProductReviews(int productReviews) {
            this.productReviews = productReviews;
        }

        // important code for loading image here
        @BindingAdapter({ "productimageurl" })
        public static void loadImage(ImageView imageView, String imageURL) {
            Glide.with(imageView.getContext())
                    .load(imageURL)
                    .placeholder(R.drawable.bisleri_bottle_img)
                    .into(imageView);
        }
    }
}
