Make sure to check out the [documentation](https://firebase.google.com/docs/guides?authuser=0) to
learn how to get started with each Firebase product that
you want to use in your app.

You can also
explore [sample Firebase apps](https://firebase.google.com/docs/samples?hl=en-US&authuser=0).

# Xoá file google-services.json (file secret) đã lỡ up lên github

```
git rm --cached app/google-services.json
git filter-branch --tree-filter 'rm -f app/google-services.json' -f -- --all
git push origin --force --all
```