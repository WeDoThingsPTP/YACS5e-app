package com.ptpthingers.grpctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ptpthingers.synchronization.DBWrapper;
import com.ptpthingers.synchronization.ManagedChannelSingleton;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;


public class GrpcLogin extends AsyncTask<String, Void, GrpcResult> {
    private Context context;

    public GrpcLogin(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GrpcResult doInBackground(String... inputParams) {
        if (inputParams.length != 3) {
            return new GrpcResult(false, "Wrong argument count: " + Integer.toString(inputParams.length));
        }

        try {
            String username = inputParams[0];
            String userpass = inputParams[1];

            ManagedChannel mChannel = ManagedChannelSingleton.getManagedChannel(this.context);

            YACS5eGrpc.YACS5eBlockingStub stub = YACS5eGrpc.newBlockingStub(mChannel);

            // creating message
            TUser user = TUser.newBuilder()
                    .setLogin(username)
                    .setPassword(userpass)
                    .build();

            // send login request. If fail, description message will be returned in catch{} block
            stub.login(user);

            DBWrapper.setOwnerForAllOrphans(username);

            // no StatusRuntimeException so user is signed in
            return new GrpcResult(true, "Successfully signed in!");
        }
        catch (StatusRuntimeException e) {
            return new GrpcResult(false, "Couldn't sign in!\n" + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(GrpcResult grpcResult) {
        super.onPostExecute(grpcResult);
    }
}