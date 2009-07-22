var Ajax;
if (Ajax && (Ajax != null)) {
    Ajax.Responders.register({
        onCreate: function() {
            if ($('spinner') && Ajax.activeRequestCount > 0)
                Effect.Appear('spinner', {duration:0.5,queue:'end'});
        },
        onComplete: function() {
            if ($('spinner') && Ajax.activeRequestCount == 0)
                Effect.Fade('spinner', {duration:0.5,queue:'end'});
        }
    });
}

var tootTipId = 0;

var Tooltip = {
    show : function(event) {
        var elem = event.element();

        var tooltipWindow = document.createElement('div');
        tooltipWindow.id = "tooltip";
        tooltipWindow.className = 'tooltip';
        tooltipWindow.style.top = Element.cumulativeOffset(elem)[1] - Element.cumulativeScrollOffset(elem)[1] - 8 + 'px';
        tooltipWindow.style.left = Element.cumulativeOffset(elem)[0] - Element.cumulativeScrollOffset(elem)[0] + 15 + 'px';

        var tooltipTop = document.createElement('div');
        tooltipTop.className = 'tooltip_top';

        var tooltipBottom = document.createElement('div');
        tooltipBottom.id = 'tooltip_' + tootTipId;

        tooltipBottom.className = 'tooltip_bottom';
        tooltipBottom.innerHTML = elem.readAttribute('longdesc');

        tooltipTop.appendChild(tooltipBottom);
        tooltipWindow.appendChild(tooltipTop);
        tooltipWindow.style.position = 'fixed';
        document.getElementsByTagName('body')[0].appendChild(tooltipWindow);

        $('tooltip_' + tootTipId).observe('mouseover', function(e) {
            window.clearTimeout(mouseouttimeout);
        });

        $('tooltip_' + tootTipId).observe('mouseout', function(e) {
            mouseouttimeout = window.setTimeout(function() {
                $('tooltip').remove();
            }, 300);
        });

        tootTipId++;
    },
    close : function(e) {
        mouseouttimeout = window.setTimeout(function() {
            $('tooltip').remove();
        }, 300)
    }
}

// Initializes the tooltips
document.observe("dom:loaded", function() {
    var tooltipps = $$('.tooltip_icon')
    for (i = 0; i < tooltipps.length; i++) {
        tooltip = tooltipps[i];
        tooltip.observe('mouseover', function(e) {
            Tooltip.show(e);
        });

        tooltip.observe('mouseout', function(e) {
            Tooltip.close(e);
        });
    }
});