# Migrate from Subversion (SVN) to Git

## Fetch the source code from SVN repository to new Git repository

### Export the list of Subversion authors

    svn log https://svn.code.sf.net/p/sweethome3d/code --quiet | awk -F '|' '/^r/ {sub("^ ", "", $2); sub(" $", "", $2); print $2" = "$2" <"$2">"}' | sort -u > authors-transform.txt

### Clone an SVN repository HEAD revision with Git 

    git svn clone https://svn.code.sf.net/p/sweethome3d/code --no-metadata --authors-file=authors-transform.txt --stdlayout --revision HEAD sweethome3d-code

### Convert version control-specific configurations
If your SVN repo was using svn:ignore properties, you can convert to a .gitignore file using:

    cd sweethome3d-code
    git svn show-ignore --id=origin/trunk > .gitignore
    git add .gitignore
    git commit -m 'Convert svn:ignore properties to .gitignore.'

### Convert all of the SVN tags into the proper Git tags
    
    for t in $(git for-each-ref --format='%(refname:short)' refs/remotes/tags); do git tag ${t/tags\//} $t && git branch -D -r $t; done

### Create local branches for each of your remote refs. Y
    
    for b in $(git for-each-ref --format='%(refname:short)' refs/remotes); do git branch $b refs/remotes/$b && git branch -D -r $b; done

#### Rename the master branch to main. 
Your main development branch will be named "trunk", which matches the name it was in Subversion. You'll want to rename it to Git's standard main branch using:

    cd sweethome3d-code
    git branch -m master main

#### Delete Subversion branch 
    cd sweethome3d-code
    git branch -D origin/trunk

#### Create a new GitHub repository using command line

    cd ..
    gh repo create sweethome3d-code --source sweethome3d-code --description "Unofficial git mirroring of SweetHome3D subversion trunk (https://svn.code.sf.net/p/sweethome3d/code/trunk/)" --homepage https://www.sweethome3d.com --push --public

- Ensure SSH keys are well configured (https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)

- gh installed on your machine and configured (gh auth login)

### Push the branch to the repository

    cd sweethome3d-code
    git push --set-upstream origin main

## Update the source code 

Now we want to update the repository on a daily basis to retrieve the last developpement

### Fetch the SVN branches to the local Git repo
    git svn fetch

### Once this is done, you’re local 'main' branch is linked to trunk/.
Make a new branch for develop & check it out

    git checkout -b develop 

### Back to master.
    git checkout main

### We'll change this to link local 'develop' branch to trunk
    git reset --hard production

## References
- https://www.gitkraken.com/blog/migrating-git-svn
- https://learn.microsoft.com/en-gb/azure/devops/repos/git/perform-migration-from-svn-to-git?view=azure-devops
- https://git-scm.com/book/fr/v2/Git-et-les-autres-syst%C3%A8mes-Migration-vers-Git
