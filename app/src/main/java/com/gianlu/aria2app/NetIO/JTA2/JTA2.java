package com.gianlu.aria2app.NetIO.JTA2;

import android.support.annotation.Nullable;

import com.gianlu.aria2app.NetIO.WebSocketing;
import com.gianlu.aria2app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JTA2 {
    private WebSocketing webSocketing;

    public JTA2(WebSocketing webSocketing) {
        this.webSocketing = webSocketing;
    }

    // Caster
    private Map<String, String> fromOptions(JSONObject jResult) throws JSONException {
        Iterator<?> keys = jResult.keys();

        Map<String, String> options = new HashMap<>();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            options.put(key, jResult.optString(key));
        }

        return options;
    }

    private List<String> fromMethods(JSONArray jResult) throws JSONException {
        List<String> methods = new ArrayList<>();

        for (int i = 0; i < jResult.length(); i++) {
            methods.add(jResult.getString(i));
        }

        return methods;
    }

    // Requests
    //aria2.addUri
    public void addUri(List<String> uris, @Nullable Integer position, @Nullable Map<String, String> options, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.addUri");
            JSONArray params = Utils.readyParams(webSocketing.getContext());

            JSONArray jUris = new JSONArray();
            for (String uri : uris) {
                jUris.put(uri);
            }
            params.put(jUris);

            JSONObject jOptions = new JSONObject();
            if (options != null) {
                for (String key : options.keySet()) {
                    jOptions.put(key, options.get(key));
                }
            }
            params.put(jOptions);

            if (position != null) params.put(position);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.addTorrent
    public void addTorrent(String base64, @Nullable List<String> uris, @Nullable Map<String, String> options, @Nullable Integer position, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.addTorrent");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(base64);

            JSONArray jUris = new JSONArray();
            if (uris != null) {
                for (String uri : uris) {
                    jUris.put(uri);
                }
            }
            params.put(jUris);

            JSONObject jOptions = new JSONObject();
            if (options != null) {
                for (String key : options.keySet()) {
                    jOptions.put(key, options.get(key));
                }
            }
            params.put(jOptions);

            if (position != null) params.put(position);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.addMetalink
    public void addMetalink(String base64, @Nullable List<String> uris, @Nullable Map<String, String> options, @Nullable Integer position, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.addMetalink");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(base64);

            JSONArray jUris = new JSONArray();
            if (uris != null) {
                for (String uri : uris) {
                    jUris.put(uri);
                }
            }
            params.put(jUris);

            JSONObject jOptions = new JSONObject();
            if (options != null) {
                for (String key : options.keySet()) {
                    jOptions.put(key, options.get(key));
                }
            }
            params.put(jOptions);

            if (position != null) params.put(position);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.tellStatus
    public void tellStatus(String gid, final IDownload handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.tellStatus");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onDownload(Download.fromString(response.getJSONObject("result")));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.getGlobalStat
    public void getGlobalStat(final IStats handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.getGlobalStat");
            request.put("params", Utils.readyParams(webSocketing.getContext()));
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onStats(GlobalStats.fromString(response.getJSONObject("result")));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.tellActive
    public void tellActive(final IDownloadList handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.tellActive");
            request.put("params", Utils.readyParams(webSocketing.getContext()));
        } catch (JSONException ex) {
            handler.onException(false, ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<Download> downloads = new ArrayList<>();
                JSONArray jResult = response.getJSONArray("result");

                for (int c = 0; c < jResult.length(); c++) {
                    downloads.add(Download.fromString(jResult.getJSONObject(c)));
                }

                handler.onDownloads(downloads);
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(q, ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(false, new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.tellWaiting
    public void tellWaiting(final IDownloadList handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.tellWaiting");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(0);
            params.put(100);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(false, ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<Download> downloads = new ArrayList<>();
                JSONArray jResult = response.getJSONArray("result");

                for (int c = 0; c < jResult.length(); c++) {
                    downloads.add(Download.fromString(jResult.getJSONObject(c)));
                }

                handler.onDownloads(downloads);
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(q, ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(false, new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.tellStopped
    public void tellStopped(final IDownloadList handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.tellStopped");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(0);
            params.put(100);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(false, ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<Download> downloads = new ArrayList<>();
                JSONArray jResult = response.getJSONArray("result");

                for (int c = 0; c < jResult.length(); c++) {
                    downloads.add(Download.fromString(jResult.getJSONObject(c)));
                }

                handler.onDownloads(downloads);
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(q, ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(false, new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.pause
    public void pause(String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.pause");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.unpause
    public void unpause(String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.unpause");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.remove
    public void remove(final String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.remove");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.removeDownloadResult
    public void removeDownloadResult(String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.removeDownloadResult");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.forcePause
    public void forcePause(String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.forcePause");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.forceRemove
    public void forceRemove(String gid, final IGID handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.forceRemove");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onGID(response.getString("result"));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.getOption
    public void getOption(String gid, final IOption handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.getOption");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onOptions(fromOptions(response.getJSONObject("result")));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.getGlobalOption
    public void getGlobalOption(final IOption handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.getGlobalOption");
            request.put("params", Utils.readyParams(webSocketing.getContext()));
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onOptions(fromOptions(response.getJSONObject("result")));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.changeOption
    public void changeOption(String gid, Map<String, String> options, final ISuccess handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.changeOption");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid);
            JSONObject jOptions = new JSONObject();
            for (Map.Entry<String, String> entry : options.entrySet()) {
                jOptions.put(entry.getKey(), entry.getValue());
            }
            params.put(jOptions);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onSuccess();
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.changePosition
    public void changePosition(String gid, int pos, POSITION_HOW how, final ISuccess handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.changePosition");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            params.put(gid)
                    .put(pos)
                    .put(how.name());
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onSuccess();
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //aria2.changeGlobalOption
    public void changeGlobalOption(Map<String, String> options, final ISuccess handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "aria2.changeGlobalOption");
            JSONArray params = Utils.readyParams(webSocketing.getContext());
            JSONObject jOptions = new JSONObject();
            for (Map.Entry<String, String> entry : options.entrySet()) {
                jOptions.put(entry.getKey(), entry.getValue());
            }
            params.put(jOptions);
            request.put("params", params);
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onSuccess();
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    //system.listMethods
    public void listMethods(final IMethod handler) {
        JSONObject request;
        try {
            request = Utils.readyRequest();
            request.put("method", "system.listMethods");
        } catch (JSONException ex) {
            handler.onException(ex);
            return;
        }

        webSocketing.send(request, new WebSocketing.IReceived() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                handler.onMethods(fromMethods(response.getJSONArray("result")));
            }

            @Override
            public void onException(boolean q, Exception ex) {
                handler.onException(ex);
            }

            @Override
            public void onException(int code, String reason) {
                handler.onException(new Aria2Exception(reason, code));
            }
        });
    }

    public enum AUTH_METHOD {
        NONE,
        HTTP,
        TOKEN
    }

    public enum POSITION_HOW {
        POS_CUR,
        POS_END,
        POS_SET
    }
}
