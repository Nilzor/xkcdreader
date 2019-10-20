# XKCD reader

This repo is the reply I make to an  [assignment](https://github.com/shortcut/coding-assignment-mobile) on creating a 
comic reader app. Below I will explain some of the choices I've made.

# Rationale for choices

## Browsing UI

One way to do this would be to create a master-detail view with a list of titles/number and allow the user to select
a title to see the comic. However most people don't know XKCD comics by hand wouldn't know what they get before they 
click. Browsing comics is better done in similar fashion as on xkcd.com where you can see "previuos", "next" or "random", 
while of course tailoring for mobile use. I'm applying the swipe motion for previous/next browsing.


## Networking

I'm using the combination of Retrofit2 with OkHttp for REST service network communication. It's a proven fast, reliable,
flexible and solid API for network communication that I am familiar with.
