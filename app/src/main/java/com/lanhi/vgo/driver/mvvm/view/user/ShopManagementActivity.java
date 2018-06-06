package com.lanhi.vgo.driver.mvvm.view.user;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.UploadFileResponse;
import com.lanhi.vgo.driver.api.response.UserInfoResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.Common;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserShopManagermentActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
import com.lanhi.ryon.utils.mutils.FileUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@Route(path = "/user/shop")
@RuntimePermissions
public class ShopManagementActivity extends BaseActivity {
    private UserShopManagermentActivityBinding binding;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_shop_managerment_activity);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });

        binding.setEvent(new OnEventListener(){
            @Override
            public void viewUpdateShopName(View v,String shopeName) {
                super.viewUpdateShopName(v,shopeName);
                ARouter.getInstance().build("/user/shop/name/edit").withString("shopeName",shopeName).navigation();
            }

            @Override
            public void updateShopImg(View v) {
                super.updateShopImg(v);
                ShopManagementActivityPermissionsDispatcher.selectPicFromLocalWithPermissionCheck(ShopManagementActivity.this);
            }
        });
        UserInfoResponse userInfoResponse = userViewModel.getUserInfoMutableLiveData().getValue();
        onDataChanged(userInfoResponse);
        userViewModel.getUserInfoMutableLiveData().observe(this, new Observer<UserInfoResponse>() {
            @Override
            public void onChanged(@Nullable UserInfoResponse userInfoResponse) {
                onDataChanged(userInfoResponse);
            }
        });
    }

    private void onDataChanged(UserInfoResponse userInfoResponse) {
        if(userInfoResponse!=null&&userInfoResponse.getData()!=null&&userInfoResponse.getData().size()>0){
            UserInfoDataBean dataBean = userInfoResponse.getData().get(0);
            if(dataBean!=null) {
                if(TextUtils.isEmpty(dataBean.getShop_duty_paragraph())) {
                    binding.setImageUrl(dataBean.getShop_head_portrait());
                }
                binding.setShopeName(dataBean.getShop_name());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ShopManagementActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void selectPicFromLocal() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme(R.style.picture.white.style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if(selectList!=null&&selectList.size()>0) {
                LocalMedia localMedia = selectList.get(0);
                String filePath = localMedia.getPath();//图片地址
                userViewModel.updateShopImg(filePath, new RObserver<UploadFileResponse>() {
                    @Override
                    public void onSuccess(UploadFileResponse uploadFileResponse) {
                        binding.setImageUrl(uploadFileResponse.getData().get(0).getImgUrl());
                    }
                });
            }
        }
    }
}
