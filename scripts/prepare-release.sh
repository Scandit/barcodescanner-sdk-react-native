#!/bin/bash

VERSION=$1

set -e

EXPECTED_ARGS=1

if [ $# -ne $EXPECTED_ARGS ]
then
echo "usage: scripts/prepare-release.sh VERSION"
exit
fi
echo "new SDK version: ${VERSION}"

update_versions() {
  the_file=docs/doxygen.conf
  sed -i.bak "s/PROJECT_NUMBER = \(.*\)/PROJECT_NUMBER = ${VERSION}/" $the_file
  rm ${the_file}.bak
  the_file=package.json
  sed -i.bak "s_\"version\":\(.*\)_\"version\": \"${VERSION}\",_" $the_file
  rm ${the_file}.bak
  the_file=samples/SimpleSample/package.json
  sed -i.bak "s_\"version\":\(.*\)_\"version\": \"${VERSION}\",_" $the_file
  rm ${the_file}.bak
  the_file=samples/ExtendedSample/package.json
  sed -i.bak "s_\"version\":\(.*\)_\"version\": \"${VERSION}\",_" $the_file
  rm ${the_file}.bak
  the_file=samples/MatrixScanSample/package.json
  sed -i.bak "s_\"version\":\(.*\)_\"version\": \"${VERSION}\",_" $the_file
  rm ${the_file}.bak
}

package_version=$(cat package.json | python -c 'import sys,json; print(json.load(sys.stdin)["version"])')
if [[ $package_version != *"SNAPSHOT" ]];
then
    echo "Tagging with version: ${package_version}"
    git tag -a $package_version -m "Tag release: ${package_version}"
    git push --tags
    echo "Tagged version ${package_version} pushed."
fi

update_versions

echo 'updated project version for react native plugin'
echo 'Please verify that the changes are correct'
git diff
echo ''
echo ''
echo 'You may commit the changes now with:'
echo "   git commit -a -m 'bump version to ${VERSION}'"
