package com.android.base.hid.util;

import android.os.ParcelUuid;
import java.util.UUID;

import androidx.annotation.NonNull;


public final class BleUuidUtils {

    private static final String UUID_LONG_STYLE_PREFIX = "0000";
    private static final String UUID_LONG_STYLE_POSTFIX = "-0000-1000-8000-00805F9B34FB";


    @NonNull
    public static UUID fromString(@NonNull final String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            // may be a short style
            return UUID.fromString(UUID_LONG_STYLE_PREFIX + uuidString + UUID_LONG_STYLE_POSTFIX);
        }
    }


    @NonNull
    public static UUID fromShortValue(final int uuidShortValue) {
        return UUID.fromString(UUID_LONG_STYLE_PREFIX + String.format("%04X", uuidShortValue & 0xffff) + UUID_LONG_STYLE_POSTFIX);
    }


    @NonNull
    public static ParcelUuid parcelFromShortValue(final int uuidShortValue) {
        return ParcelUuid.fromString(UUID_LONG_STYLE_PREFIX + String.format("%04X", uuidShortValue & 0xffff) + UUID_LONG_STYLE_POSTFIX);
    }


    public static int toShortValue(@NonNull UUID uuid) {
        return (int)(uuid.getMostSignificantBits() >> 32 & 0xffff);
    }


    public static boolean matches(@NonNull final UUID src, @NonNull final UUID dst) {
        if (isShortUuid(src) || isShortUuid(dst)) {
            // at least one instance is short style: check only 16bits
            final long srcShortUUID = src.getMostSignificantBits() & 0x0000ffff00000000L;
            final long dstShortUUID = dst.getMostSignificantBits() & 0x0000ffff00000000L;

            return srcShortUUID == dstShortUUID;
        } else {
            return src.equals(dst);
        }
    }


    private static boolean isShortUuid(@NonNull final UUID src) {
        return (src.getMostSignificantBits() & 0xffff0000ffffffffL) == 0L && src.getLeastSignificantBits() == 0L;
    }
}
