# Instructions related to front end

## Adding loading animation

To add loading animation, include `loading.css` to the html file (or copy the snippet below)

```
<link rel="stylesheet" type="text/css" href="css/loading.css" />
```

Once `loading.css` is included, paste the following snippet to where you want the animation to be during the ajax call

```
<div class="sk-chase">
  <!-- Loading animation -->
  <div class="sk-chase-dot"></div>
  <div class="sk-chase-dot"></div>
  <div class="sk-chase-dot"></div>
  <div class="sk-chase-dot"></div>
  <div class="sk-chase-dot"></div>
  <div class="sk-chase-dot"></div>
</div>
```

In the associated `.js` that handles the ajax call, add the following snippet of code in the ajax call (if unclear, you can reference `index.js`)

```
beforeSend: function () {
  $(".sk-chase").css("display", "block");
},

complete: function () {
  $(".sk-chase").css("display", "none");
},
```

Note: if the loading animataion dimension is too big, you can edit the `width` and `height` of `.sk-chase` to something smaller (default is `40px`). When changing dimension, make sure to set the dimension of `width` and `height` to be the same, or else the animation wouldn't load in a circular motion

Source of loading animation [here](https://tobiasahlin.com/spinkit/)
