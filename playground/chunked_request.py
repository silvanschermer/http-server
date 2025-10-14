#!/usr/bin/env python3
import http.client

import time

conn = http.client.HTTPConnection("localhost", 80, timeout=10)
conn.putrequest("POST", "/your-endpoint", skip_host=True)
conn.putheader("Host", "localhost")
conn.putheader("Transfer-Encoding", "chunked")
conn.putheader("Content-Type", "text/plain")
conn.putheader("Connection", "close")
conn.endheaders()


def send_chunk(b: bytes):
    conn.send(("%X\r\n" % len(b)).encode("ascii"))
    conn.send(b)
    conn.send(b"\r\n")


send_chunk(b"first part\n")
time.sleep(1)
send_chunk(b"second part\n")
time.sleep(1)
conn.send(b"0\r\n\r\n")  # correct terminator
resp = conn.getresponse()
print(resp.status, resp.reason)
print(resp.read().decode(errors="ignore"))
conn.close()
