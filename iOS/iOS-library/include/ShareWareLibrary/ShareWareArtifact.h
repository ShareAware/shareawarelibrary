//
//  ShareWareArtifact.h
//  ShareWareLibraryApp
//

#import <Foundation/Foundation.h>

/**
 * Artifact data structure
 */
@interface ShareWareArtifact : NSObject {
    NSString *name;
    NSString *permission;
    NSString *sharedWith;
}

@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSString *permission;
@property (strong, nonatomic) NSString *sharedWith;

@end

#define __SHARE__


