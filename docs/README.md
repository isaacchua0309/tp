# FitFriends Theme Documentation
This site is built using GitHub Pages with Jekyll.

## Theme Information

This site uses the `jekyll-theme-primer` theme, which is one of the supported themes for GitHub Pages.

## Troubleshooting Theme Issues

If you encounter theme-related build errors:

1. Ensure that the theme specified in `_config.yml` matches the theme actually used by GitHub Pages
2. Make sure `docs/assets/css/style.scss` imports stylesheets that are compatible with the chosen theme
3. If using custom styles, make sure they don't conflict with the theme

## Local Development

To test this documentation site locally:

1. Install Ruby and Bundler
2. Navigate to the `docs` directory
3. Run `bundle install` to install dependencies
4. Run `bundle exec jekyll serve` to start a local server
5. Visit `http://localhost:4000` to view the site

For more information, see the [GitHub Pages documentation](https://docs.github.com/en/pages). 