package hphuc.project.visafe_version1.vi_safe.app.network.header;

public interface HeaderConst {
    String SECRET_KEY = "najvj5u2sd5svty";
    String SECRET_KEY_UAT = SECRET_KEY;
    String SECRET_KEY_PRODUCT = SECRET_KEY;

    String AUTHOR = "Authorization";
    String UNIPRIME_DEVICE = "MNV-DEVICE";
    String UNIPRIME_ENCODE = "MNV-ENCODE";
    String X_CONTENT_TYPE = "Content-Type";
    String ACCEPT = "accept";
    String CONNECTION ="Connection";
    String CACHE = "Cache-Control";
    String CONTENT_ENCODE ="Content-Encoding";
    //value
    String CACHE_VALUE= "Cache-Control";
    String X_CONTENT_TYPE_VALUE = "application/json; charset=utf-8";
    String ACCEPT_VALUE = "application/json, text/json";
    String CONNECTION_VALUE = "close";
    String CONTENT_ENCODE_VALUE ="gzip";


}
