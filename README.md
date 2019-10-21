# XKCD reader

This repo is the reply I make to an  [assignment](https://github.com/shortcut/coding-assignment-mobile) on creating a 
comic reader app. Below I will explain some of the choices I've made.

## How to use

- Swipe right to browse older comics. Left for newer. The app starts leftmost at the newest comic.
- Tap image for details

# Rationale for choices

## Browsing UI

One way to do this would be to create a master-detail view with a list of titles/number and allow the user to select
a title to see the comic. However most people don't know XKCD comics by hand wouldn't know what they get before they 
click. Browsing comics is better done in similar fashion as on xkcd.com where you can see "previuos", "next" or "random", 
while of course tailoring for mobile use. I'm applying the swipe motion for previous/next browsing.

## Networking

I'm using the combination of Retrofit2 with OkHttp for REST service network communication. It's a proven fast, reliable,
flexible and solid API for network communication that I am familiar with.

## Image loading

I'm using the Glide library as we've tested it in production with my current project which is heavily dependent
on performant image loading. It is solid and well maintained. 

## Model and view architecture

- I use two layers for the data: 
  - Network layer (.networking.content): 1-1 represenatation of the JSON data from the remote endpoint
  - Domain layer (.domainmodels): Representation of the data as it makes sense with strong Kotlin data types.

This allows me to unit test conversion and parsing before it hits the UI layer.

- For View Architecture I settled at not implementine neither MVP or MVVM as I felt the task was sufficient simple to
omit it. Separation between domain and network layers allowed me to put a lot of business logic outside of the Fragment
and Activity classes still. For a production app aimed at living longer than this app, I'd definitely go for a view 
pattern, possibly MVVM, to allow for better handling and better maintainability.   

# Improvement points

Here are some areas that I see need improvement:

- UI could need better hints for what navigation patterns are available
- No state is saved, so the app will reset after process kill
- When transcript is present, the scrollview might fight with the bottom sheet for vertical scroll
- The click to show the comic details is a bit sluggish, unknown why
